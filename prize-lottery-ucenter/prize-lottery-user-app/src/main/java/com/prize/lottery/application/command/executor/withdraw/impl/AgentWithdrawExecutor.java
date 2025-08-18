package com.prize.lottery.application.command.executor.withdraw.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.application.assembler.WithdrawAssembler;
import com.prize.lottery.application.command.dto.WithdrawDto;
import com.prize.lottery.application.command.executor.withdraw.WithdrawExecutor;
import com.prize.lottery.application.consumer.event.TransCallbackEvent;
import com.prize.lottery.domain.agent.model.AgentAcctDo;
import com.prize.lottery.domain.agent.repository.IAgentAcctRepository;
import com.prize.lottery.domain.agent.repository.IAgentWithdrawRepository;
import com.prize.lottery.domain.user.model.UserWithdrawDo;
import com.prize.lottery.domain.withdraw.specs.WithdrawRuleSpec;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import com.prize.lottery.infrast.persist.enums.WithdrawState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AgentWithdrawExecutor implements WithdrawExecutor {

    private final WithdrawAssembler        withdrawAssembler;
    private final IAgentAcctRepository     userAgentRepository;
    private final IAgentWithdrawRepository agentWithdrawRepository;

    /**
     * 支付场景
     */
    @Override
    public TransferScene bizIndex() {
        return TransferScene.USER_AGENT_TRANS;
    }

    @Override
    public void execute(WithdrawDto withdraw, WithdrawRuleSpec ruleSpec) {
        //流量主账户
        AgentAcctDo agentAcct = userAgentRepository.ofId(withdraw.getUserId())
                                                   .orElseThrow(ResponseHandler.USER_INVITE_NONE);
        //提现计算
        agentAcct.withdraw(ruleSpec, withdraw.getAmount());
        userAgentRepository.save(agentAcct);
        //保存提现记录
        UserWithdrawDo userWithdraw = new UserWithdrawDo(withdraw, withdrawAssembler::toDo);
        AggregateFactory.create(userWithdraw).save(agentWithdrawRepository::save);
    }

    @Override
    public void callbackHandle(TransCallbackEvent event) {
        Aggregate<Long, UserWithdrawDo> aggregate = agentWithdrawRepository.ofSeqNo(event.getBizNo())
                                                                           .filter(agg -> agg.getRoot().isDoing())
                                                                           .orElse(null);
        if (aggregate == null) {
            return;
        }
        UserWithdrawDo root  = aggregate.getRoot();
        WithdrawState  state = WithdrawState.ofValue(event.getState());
        //更新提现状态
        root.modify(event.getTransNo(), state, event.getMessage());
        agentWithdrawRepository.save(aggregate);
        //提现失败回退账户余额
        if (event.isFailure()) {
            userAgentRepository.ofId(event.getUserId()).ifPresent(balance -> {
                balance.withdrawRollback(root.toValObj());
                userAgentRepository.save(balance);
            });
        }
    }

}
