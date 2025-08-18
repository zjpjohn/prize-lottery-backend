package com.prize.lottery.application.command.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.event.publisher.DomainEventPublisher;
import com.cloud.arch.utils.IdWorker;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.assembler.WithdrawAssembler;
import com.prize.lottery.application.command.IWithdrawCommandService;
import com.prize.lottery.application.command.dto.*;
import com.prize.lottery.application.command.event.WithdrawPayEvent;
import com.prize.lottery.application.command.executor.withdraw.WithdrawExecutorFactory;
import com.prize.lottery.application.consumer.event.TransCallbackEvent;
import com.prize.lottery.domain.user.model.UserInfo;
import com.prize.lottery.domain.user.repository.IUserInfoRepository;
import com.prize.lottery.domain.withdraw.model.WithdrawLevelDo;
import com.prize.lottery.domain.withdraw.model.WithdrawRuleDo;
import com.prize.lottery.domain.withdraw.repository.IWithdrawLevelRepository;
import com.prize.lottery.domain.withdraw.repository.IWithdrawRuleRepository;
import com.prize.lottery.domain.withdraw.specs.WithdrawRuleSpec;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WithdrawCommandService implements IWithdrawCommandService {

    private final WithdrawAssembler        withdrawAssembler;
    private final IUserInfoRepository      userInfoRepository;
    private final IWithdrawRuleRepository  withdrawRuleRepository;
    private final WithdrawExecutorFactory  withdrawExecutorFactory;
    private final IWithdrawLevelRepository withdrawLevelRepository;

    @Override
    @Transactional
    public void createRule(WithRuleCreateCmd command) {
        WithdrawRuleDo withdrawRule = new WithdrawRuleDo(command.validate(), withdrawAssembler::toDo);
        AggregateFactory.create(withdrawRule).save(withdrawRuleRepository::save);
    }

    @Override
    @Transactional
    public void modifyRule(WithRuleEditCmd command) {
        withdrawRuleRepository.ofId(command.getId())
                              .peek(root -> root.modify(command, withdrawAssembler::toDo))
                              .save(withdrawRuleRepository::save);
    }

    @Override
    public void createWithdrawLevel(WithLevelCreateCmd command) {
        WithdrawLevelDo withdrawLevel = new WithdrawLevelDo(command.getScene(), command.getLevels(), command.getRemark());
        AggregateFactory.create(withdrawLevel).save(withdrawLevelRepository::save);
    }

    @Override
    public void editWithdrawLevel(WithLevelEditCmd command) {
        withdrawLevelRepository.ofId(command.getId())
                               .peek(root -> root.modify(command.getLevels(), command.getState(), command.getRemark()))
                               .save(withdrawLevelRepository::save);
    }

    @Override
    @Transactional
    public void payWithdraw(WithdrawCreateCmd command, TransferScene scene) {
        //用户信息判断
        Aggregate<Long, UserInfo> aggregate = userInfoRepository.ofId(command.getUserId());
        Assert.notNull(aggregate, ResponseHandler.USER_INFO_NONE);

        //账户有效性校验
        UserInfo userInfo = aggregate.getRoot();
        Assert.notNull(userInfo.isEnable(), ResponseHandler.WITHDRAW_FORBIDDEN);
        //渠道支付参数校验
        Pair<Boolean, String> canWithdraw = userInfo.canWithdraw(command.getChannel());
        Assert.state(canWithdraw.getKey(), ResponseHandler.WITHDRAW_CHANNEL_NONE);

        //提现场景规则
        WithdrawRuleSpec ruleSpec = withdrawRuleRepository.ofScene(scene.getScene())
                                                          .orElseThrow(Assert.supply(ResponseHandler.WITHDRAW_ILLEGAL));
        //支付本地业务处理
        WithdrawDto withdraw = new WithdrawDto();
        withdraw.setScene(scene);
        withdraw.setUserId(userInfo.getId());
        withdraw.setChannel(command.getChannel());
        withdraw.setAmount(command.getAmount());
        withdraw.setSeqNo(String.valueOf(IdWorker.nextId()));
        withdrawExecutorFactory.execute(withdraw, ruleSpec);

        //发送转账支付事件
        WithdrawPayEvent event = new WithdrawPayEvent();
        event.setUserId(userInfo.getId());
        event.setOpenId(canWithdraw.getValue());
        event.setBizNo(withdraw.getSeqNo());
        event.setAmount(command.getAmount());
        event.setScene(scene.getScene());
        event.setChannel(command.getChannel().getChannel());
        DomainEventPublisher.publish(event);
    }

    @Override
    @Transactional
    public void withdrawRollback(TransCallbackEvent event) {
        withdrawExecutorFactory.callbackHandle(event);
    }

}
