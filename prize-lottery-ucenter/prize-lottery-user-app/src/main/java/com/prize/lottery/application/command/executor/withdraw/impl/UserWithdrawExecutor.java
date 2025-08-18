package com.prize.lottery.application.command.executor.withdraw.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.prize.lottery.application.assembler.WithdrawAssembler;
import com.prize.lottery.application.command.dto.WithdrawDto;
import com.prize.lottery.application.command.executor.withdraw.WithdrawExecutor;
import com.prize.lottery.application.consumer.event.TransCallbackEvent;
import com.prize.lottery.domain.user.model.UserBalance;
import com.prize.lottery.domain.user.model.UserWithdrawDo;
import com.prize.lottery.domain.user.repository.IUserBalanceRepository;
import com.prize.lottery.domain.user.repository.IUserWithdrawRepository;
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
public class UserWithdrawExecutor implements WithdrawExecutor {

    private final WithdrawAssembler       withdrawAssembler;
    private final IUserBalanceRepository  balanceRepository;
    private final IUserWithdrawRepository withdrawRepository;

    /**
     * 支付场景
     */
    @Override
    public TransferScene bizIndex() {
        return TransferScene.USER_REWARD_TRANS;
    }

    public void execute(WithdrawDto withdraw, WithdrawRuleSpec ruleSpec) {
        //用户收益账户
        UserBalance userBalance = balanceRepository.ofId(withdraw.getUserId())
                                                   .orElseThrow(ResponseHandler.USER_INFO_NONE);
        //账户提现计算
        userBalance.withdraw(ruleSpec, withdraw.getAmount());
        balanceRepository.save(userBalance);
        //保存提现记录
        UserWithdrawDo userWithdraw = new UserWithdrawDo(withdraw, withdrawAssembler::toDo);
        AggregateFactory.create(userWithdraw).save(withdrawRepository::save);
    }

    @Override
    public void callbackHandle(TransCallbackEvent event) {
        Aggregate<Long, UserWithdrawDo> aggregate = withdrawRepository.ofSeqNo(event.getBizNo())
                                                                      .filter(agg -> agg.getRoot().isDoing())
                                                                      .orElse(null);
        if (aggregate == null) {
            return;
        }
        UserWithdrawDo root  = aggregate.getRoot();
        WithdrawState  state = WithdrawState.ofValue(event.getState());
        //更新提现状态
        root.modify(event.getTransNo(), state, event.getMessage());
        withdrawRepository.save(aggregate);
        if (event.isFailure()) {
            //提现失败回退账户余额
            balanceRepository.ofId(event.getUserId()).ifPresent(balance -> {
                balance.withdrawRollback(root.toValObj());
                balanceRepository.save(balance);
            });
        }
    }
}
