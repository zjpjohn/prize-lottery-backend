package com.prize.lottery.application.command.executor.withdraw.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.application.assembler.WithdrawAssembler;
import com.prize.lottery.application.command.dto.WithdrawDto;
import com.prize.lottery.application.command.executor.withdraw.WithdrawExecutor;
import com.prize.lottery.application.consumer.event.TransCallbackEvent;
import com.prize.lottery.domain.expert.model.ExpertBalance;
import com.prize.lottery.domain.expert.repository.IExpertBalanceRepository;
import com.prize.lottery.domain.expert.repository.IExpertWithdrawRepository;
import com.prize.lottery.domain.user.model.UserWithdrawDo;
import com.prize.lottery.domain.withdraw.specs.WithdrawRuleSpec;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import com.prize.lottery.infrast.persist.enums.WithdrawState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExpertWithdrawExecutor implements WithdrawExecutor {

    private final WithdrawAssembler         withdrawAssembler;
    private final IExpertBalanceRepository  expertBalanceRepository;
    private final IExpertWithdrawRepository expertWithdrawRepository;

    /**
     * 支付场景
     */
    @Override
    public TransferScene bizIndex() {
        return TransferScene.USER_EXPERT_TRANS;
    }

    @Override
    @Transactional
    public void execute(WithdrawDto withdraw, WithdrawRuleSpec ruleSpec) {
        ExpertBalance balance = expertBalanceRepository.ofId(withdraw.getUserId())
                                                       .orElseThrow(ResponseHandler.USER_MASTER_NONE);

        //计算提现金额
        balance.withdraw(ruleSpec, withdraw.getAmount());
        expertBalanceRepository.save(balance);

        //保存提现记录
        UserWithdrawDo userWithdraw = new UserWithdrawDo(withdraw, withdrawAssembler::toDo);
        AggregateFactory.create(userWithdraw).save(expertWithdrawRepository::save);
    }

    @Override
    public void callbackHandle(TransCallbackEvent event) {
        Aggregate<Long, UserWithdrawDo> aggregate = expertWithdrawRepository.ofSeqNo(event.getBizNo())
                                                                            .filter(agg -> agg.getRoot().isDoing())
                                                                            .orElse(null);
        if (aggregate == null) {
            return;
        }
        UserWithdrawDo root  = aggregate.getRoot();
        WithdrawState  state = WithdrawState.ofValue(event.getState());
        //更新提现状态
        root.modify(event.getTransNo(), state, event.getMessage());
        expertWithdrawRepository.save(aggregate);
        //提现失败回退账户余额
        if (event.isFailure()) {
            expertBalanceRepository.ofId(event.getUserId()).ifPresent(balance -> {
                balance.withdrawRollback(root.toValObj());
                expertBalanceRepository.save(balance);
            });
        }
    }
}
