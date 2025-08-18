package com.prize.lottery.domain.withdraw.specs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@AllArgsConstructor
public class WithdrawRuleSpec {

    private final Long    throttle;
    private final Long    maximum;
    private final Integer interval;

    /**
     * 提现金额是否满足提现要求
     *
     * @param amount 提现金额
     */
    public boolean canWithdraw(Long amount) {
        return amount >= throttle;
    }

    /**
     * 金额校验
     *
     * @param amount 待校验金额
     */
    public boolean satisfy(Long amount, LocalDate lastTrans) {
        if (lastTrans != null) {
            LocalDate current = LocalDate.now();
            if (ChronoUnit.DAYS.between(lastTrans, current) < interval) {
                return false;
            }
        }
        return amount >= throttle && amount <= maximum;
    }

}
