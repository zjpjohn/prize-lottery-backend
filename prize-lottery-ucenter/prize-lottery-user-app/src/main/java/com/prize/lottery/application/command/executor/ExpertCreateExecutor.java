package com.prize.lottery.application.command.executor;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.ExpertAcctAssembler;
import com.prize.lottery.application.command.dto.ExpertCreateCmd;
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
public class ExpertCreateExecutor {

    private final IMobileQueryService   mobileQueryService;
    private final IUserInfoRepository   userInfoRepository;
    private final IExpertAcctRepository expertAcctRepository;
    private final ExpertAcctAssembler   assembler;

    @Transactional
    public void execute(ExpertCreateCmd command) {
        //验证码校验
        Aggregate<Long, UserInfo> aggregate = userInfoRepository.ofId(command.getUserId());
        Assert.notNull(aggregate, ResponseHandler.USER_INFO_NONE);

        UserInfo userInfo    = aggregate.getRoot();
        Boolean  checkResult = mobileQueryService.checkSmsCode(userInfo.getPhone(), command.getCode(), SmsChannel.RESET_PWD);
        Assert.state(checkResult, ResponseHandler.VERIFY_CODE_ERROR);

        //创建专家账户
        ExpertAcct expertAcct = new ExpertAcct(command.getUserId(), command.getPassword());
        expertAcctRepository.save(expertAcct);
        //设置用户专家标识
        aggregate.peek(UserInfo::enableExpert).save(userInfoRepository::save);
    }

}
