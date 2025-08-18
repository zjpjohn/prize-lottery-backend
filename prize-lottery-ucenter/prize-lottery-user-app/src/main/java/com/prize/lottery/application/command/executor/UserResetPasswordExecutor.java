package com.prize.lottery.application.command.executor;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.UserResetPwdCmd;
import com.prize.lottery.application.query.IMobileQueryService;
import com.prize.lottery.domain.user.model.UserInfo;
import com.prize.lottery.dto.SmsChannel;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.repository.impl.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserResetPasswordExecutor {

    private final IMobileQueryService mobileQueryService;
    private final UserInfoRepository  userInfoRepository;

    @Transactional
    public void execute(UserResetPwdCmd command) {
        //账户校验
        Aggregate<Long, UserInfo> aggregate = userInfoRepository.ofId(command.getUserId());
        Assert.notNull(aggregate, ResponseHandler.USER_INFO_NONE);
        //验证码校验
        UserInfo userInfo    = aggregate.getRoot();
        boolean  checkResult = mobileQueryService.checkSmsCode(userInfo.getPhone(), command.getCode(), SmsChannel.RESET_PWD);
        Assert.state(checkResult, ResponseHandler.VERIFY_CODE_ERROR);
        //重置登录密码
        userInfo.resetPwd(command.getPassword());
        userInfoRepository.save(aggregate);
    }

}
