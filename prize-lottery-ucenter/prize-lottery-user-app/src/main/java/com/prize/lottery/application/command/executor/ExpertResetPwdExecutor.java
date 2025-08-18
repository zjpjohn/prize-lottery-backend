package com.prize.lottery.application.command.executor;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.ExpertResetPwdCmd;
import com.prize.lottery.application.query.IMobileQueryService;
import com.prize.lottery.domain.expert.model.ExpertAcct;
import com.prize.lottery.domain.expert.repository.IExpertAcctRepository;
import com.prize.lottery.domain.user.model.UserInfo;
import com.prize.lottery.domain.user.repository.IUserInfoRepository;
import com.prize.lottery.dto.SmsChannel;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExpertResetPwdExecutor {

    private final IMobileQueryService   mobileQueryService;
    private final IUserInfoRepository   userInfoRepository;
    private final IExpertAcctRepository expertAcctRepository;

    @Transactional
    public void execute(ExpertResetPwdCmd command) {
        //账户校验
        ExpertAcct account = expertAcctRepository.ofUser(command.getUserId());
        Assert.notNull(account, ResponseHandler.USER_MASTER_NONE);
        //验证码校验
        Aggregate<Long, UserInfo> aggregate = userInfoRepository.ofId(command.getUserId());
        Assert.notNull(aggregate, ResponseHandler.USER_INFO_NONE);

        UserInfo userInfo    = aggregate.getRoot();
        Boolean  checkResult = mobileQueryService.checkSmsCode(userInfo.getPhone(), command.getCode(), SmsChannel.RESET_PWD);
        Assert.state(checkResult, ResponseHandler.VERIFY_CODE_ERROR);

        //持久化账户支付密码
        ExpertAcct expertAcct = account.resetPwd(command.getPwd());
        expertAcctRepository.save(expertAcct);
    }

}
