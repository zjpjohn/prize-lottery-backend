package com.prize.lottery.application.command.executor;

import com.cloud.arch.Ip2RegionSearcher;
import com.cloud.arch.IpRegionResult;
import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.web.ITokenBlackListPublisher;
import com.cloud.arch.web.ITokenCreator;
import com.cloud.arch.web.TokenResult;
import com.prize.lottery.LotteryAuthSource;
import com.prize.lottery.application.command.dto.AdminLoginCmd;
import com.prize.lottery.application.vo.AdminAuthVo;
import com.prize.lottery.domain.admin.model.AdminLogin;
import com.prize.lottery.domain.admin.model.Administrator;
import com.prize.lottery.domain.admin.repository.IAdminLoginRepository;
import com.prize.lottery.domain.admin.repository.IAdministratorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminAuthenticateExe {

    private final LotteryAuthSource        authSource;
    private final ITokenCreator            jwtTokenCreator;
    private final IAdministratorRepository administratorRepository;
    private final IAdminLoginRepository    adminLoginRepository;
    private final ITokenBlackListPublisher blackTokenPublisher;
    private final Ip2RegionSearcher        ip2RegionSearcher;

    /**
     * 管理员登录
     */
    @Transactional(rollbackFor = Exception.class)
    public AdminAuthVo execute(AdminLoginCmd command) {
        //登录校验
        Aggregate<Long, Administrator> aggregate     = administratorRepository.ofName(command.getName());
        Administrator                  administrator = aggregate.getRoot();
        administrator.authCheck(command.getPassword());
        //上一次登录的tokenId以及过期截止时间
        AdminLogin    adminLogin  = adminLoginRepository.of(administrator.getId());
        String        oldTokenId  = adminLogin.getTokenId();
        LocalDateTime oldExpireAt = adminLogin.getExpireAt();
        //授权登录并生成token
        TokenResult result = adminLogin.login(jwtTokenCreator, authSource, this::ip2Region);
        adminLoginRepository.save(adminLogin);
        //上一次登录的token未失效，将未失效的token加入黑名单中标记失效
        if (StringUtils.isNotBlank(oldTokenId) && oldExpireAt.isAfter(LocalDateTime.now())) {
            blackTokenPublisher.publish(oldTokenId, oldExpireAt);
        }
        //构造授权登录结果
        AdminAuthVo response = new AdminAuthVo();
        response.role(administrator.getLevel());
        response.setName(administrator.getName());
        response.setToken(result.token());
        response.setExpireAt(result.expireAt().getTime());
        return response;
    }

    private String ip2Region(String ip) {
        return Optional.ofNullable(ip).map(ip2RegionSearcher::search).map(IpRegionResult::getAddress).orElse("");
    }
}
