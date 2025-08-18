package com.prize.lottery.application.command.executor;

import com.cloud.arch.Ip2RegionSearcher;
import com.cloud.arch.IpRegionResult;
import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.web.ITokenBlackListPublisher;
import com.cloud.arch.web.ITokenCreator;
import com.cloud.arch.web.TokenResult;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.LotteryAuthSource;
import com.prize.lottery.application.assembler.UserInfoAssembler;
import com.prize.lottery.application.command.dto.BaseUserAuthCmd;
import com.prize.lottery.application.command.dto.UserPwdAuthCmd;
import com.prize.lottery.application.command.vo.UserLoginResult;
import com.prize.lottery.domain.user.ability.UserDomainService;
import com.prize.lottery.domain.user.model.UserInfo;
import com.prize.lottery.domain.user.model.UserInvite;
import com.prize.lottery.domain.user.model.UserLogin;
import com.prize.lottery.domain.user.repository.IUserInfoRepository;
import com.prize.lottery.domain.user.repository.IUserInviteRepository;
import com.prize.lottery.domain.user.repository.IUserLoginRepository;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class UserAuthLoginExecutor {

    private final LotteryAuthSource        authSource;
    private final ITokenCreator            jwtTokenCreator;
    private final UserInfoAssembler        userInfoAssembler;
    private final IUserInfoRepository      userInfoRepository;
    private final IUserLoginRepository     userLoginRepository;
    private final IUserInviteRepository    userInviteRepository;
    private final UserDomainService        userDomainService;
    private final ITokenBlackListPublisher blockTokenPublisher;
    private final Ip2RegionSearcher        ip2RegionSearcher;

    /**
     * 登录注册通用功能
     */
    @Transactional(rollbackFor = Exception.class)
    public UserLoginResult execute(BaseUserAuthCmd command) {
        //登录设备校验
        boolean existed = userLoginRepository.existDevice(command.getDeviceId());
        Assert.state(existed, ResponseHandler.ILLEGAL_APP_DEVICE);
        //登录账户构造，未登录账户自动注册
        Pair<UserInfo, UserInvite> pair = userInfoRepository.ofPhone(command.getPhone()).map(aggregate -> {
            UserInfo   userInfo = aggregate.getRoot();
            UserInvite invite   = userInviteRepository.ofId(userInfo.getId()).getRoot();
            Assert.notNull(invite, ResponseHandler.USER_INVITE_NONE);
            return Pair.of(userInfo, invite);
        }).orElseGet(() -> userDomainService.userRegister(command));
        //账户登录授权处理
        return this.authLogin(pair.getKey(), pair.getValue(), command.getDeviceId());
    }

    /**
     * 账号密码登录校验
     */
    @Transactional(rollbackFor = Exception.class)
    public UserLoginResult execute(UserPwdAuthCmd command) {
        //登录设备校验
        boolean existed = userLoginRepository.existDevice(command.getDeviceId());
        Assert.state(existed, ResponseHandler.ILLEGAL_APP_DEVICE);
        //手机账号已注册才允许登陆
        UserInfo userInfo = userInfoRepository.ofPhone(command.getPhone())
                                              .map(Aggregate::getRoot)
                                              .orElseThrow(Assert.supply(ResponseHandler.USER_INFO_NONE));
        //账户登录密码校验
        Assert.state(userInfo.pwdValidate(command.getPassword()), ResponseHandler.PASSWORD_ERROR);
        //邀请账户信息
        UserInvite userInvite = userInviteRepository.ofId(userInfo.getId()).getRoot();
        Assert.notNull(userInvite, ResponseHandler.USER_INVITE_NONE);

        return this.authLogin(userInfo, userInvite, command.getDeviceId());
    }

    /**
     * 账户登录处理
     *
     * @param userInfo 账户信息
     */
    private UserLoginResult authLogin(UserInfo userInfo, UserInvite invite, String deviceId) {
        //用户状态校验
        Assert.state(userInfo.isEnable(), ResponseHandler.ACCOUNT_EXCEPTION);
        // todo 过期上一次登录信息，可以异步实现缩短响应时间
        UserLogin     userLogin   = userLoginRepository.ofId(userInfo.getId());
        String        oldTokenId  = userLogin.getTokenId();
        LocalDateTime oldExpireAt = userLogin.getExpireAt();
        //登录校验保存本次token生成标识
        TokenResult result = userLogin.login(jwtTokenCreator, authSource, deviceId, this::ip2Region);
        userLoginRepository.save(userLogin);
        //失效上一次登录token，将tokenId加入黑名单中
        if (StringUtils.isNotBlank(oldTokenId) && oldExpireAt.isAfter(LocalDateTime.now())) {
            blockTokenPublisher.publish(oldTokenId, userLogin.getExpireAt());
        }
        //构造登录返回信息
        UserLoginResult response = userInfoAssembler.toVo(userInfo, result.token(), result.expireAt().getTime());
        String                   uid    = String.valueOf(userInfo.getId());
        String                   userId = Base64.getEncoder().encodeToString(uid.getBytes(StandardCharsets.UTF_8));
        UserLoginResult.UserInfo user   = response.getUser();
        user.setUid(userId);
        user.setCode(invite.getCode());
        user.setInviteUri(invite.getInvUri());
        response.setLoginTime(LocalDateTime.now());
        return response;
    }

    private String ip2Region(String ip) {
        return Optional.ofNullable(ip).map(ip2RegionSearcher::search).map(IpRegionResult::getAddress).orElse("");
    }

}
