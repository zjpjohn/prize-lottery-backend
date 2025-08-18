package com.prize.lottery.domain.expert.valobj;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter(AccessLevel.PRIVATE)
public class ExpertOperation {

    private Long      userId;
    private Long      income;
    private Long      withdraw;
    private Long      withRmb;
    private LocalDate withLatest;

    public static ExpertOperation income(Long userId, Long income) {
        ExpertOperation operation = new ExpertOperation();
        operation.setUserId(userId);
        operation.setIncome(income);
        return operation;
    }

    public static ExpertOperation withdraw(Long userId, Long withdraw, Long withRmb) {
        ExpertOperation operation = new ExpertOperation();
        operation.setUserId(userId);
        operation.setIncome(-withdraw);
        operation.setWithdraw(withdraw);
        operation.setWithRmb(withRmb);
        operation.setWithLatest(LocalDate.now());
        return operation;
    }

    public static ExpertOperation rollback(Long userId, Long withdraw, Long withRmb) {
        ExpertOperation operation = new ExpertOperation();
        operation.setUserId(userId);
        operation.setIncome(withdraw);
        operation.setWithdraw(-withdraw);
        operation.setWithRmb(-withRmb);
        return operation;
    }

}
