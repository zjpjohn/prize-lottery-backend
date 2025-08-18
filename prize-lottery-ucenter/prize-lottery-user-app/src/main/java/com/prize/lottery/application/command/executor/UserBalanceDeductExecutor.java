package com.prize.lottery.application.command.executor;

import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.application.consumer.event.BalanceDeductEvent;
import com.prize.lottery.domain.agent.ability.AgentIncomeAbility;
import com.prize.lottery.domain.user.model.UserBalance;
import com.prize.lottery.domain.user.repository.IUserBalanceRepository;
import com.prize.lottery.domain.voucher.model.aggregate.BatchUserVoucherDo;
import com.prize.lottery.domain.voucher.repository.IUserVoucherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserBalanceDeductExecutor {

    private final IUserBalanceRepository balanceRepository;
    private final IUserVoucherRepository voucherRepository;
    private final AgentIncomeAbility     agentIncomeAbility;

    /**
     * 账户余额扣减操作
     */
    @Transactional
    public void execute(BalanceDeductEvent event) {
        UserBalance balance = balanceRepository.ofId(event.getUserId()).orElse(null);
        if (balance == null || !balance.hasEnough(event.getExpend())) {
            return;
        }
        //扣减代金券抵扣
        int voucher = this.useBalanceVoucher(balance);
        //预扣减非提现金币
        int surplus = UserBalance.SURPLUS_THROTTLE - voucher;
        //扣减账户奖励金
        Integer deductBounty = balance.deduct(event.getExpend(), voucher, surplus, event.getEventRemark());
        //保存账户余额
        balanceRepository.save(balance);
        //流量主分享获得消费奖励金分成
        if (balance.canProfited()) {
            agentIncomeAbility.execute(event.getSeqNo(), event.getUserId(), deductBounty, event.getSource());
        }
    }

    private int useBalanceVoucher(UserBalance balance) {
        if (balance.getVoucher() <= 0) {
            return 0;
        }
        Long                                userId           = balance.getUserId();
        Aggregate<Long, BatchUserVoucherDo> aggregate        = voucherRepository.ofUser(userId, 6);
        BatchUserVoucherDo                  batchUserVoucher = aggregate.getRoot();
        //计算使用代金券金额
        int voucher = batchUserVoucher.useVouchers(userId, UserBalance.SURPLUS_THROTTLE);
        voucherRepository.save(aggregate);
        return voucher;
    }

}
