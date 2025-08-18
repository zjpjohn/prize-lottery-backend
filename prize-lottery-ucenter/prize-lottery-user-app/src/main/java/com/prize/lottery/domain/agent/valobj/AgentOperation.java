package com.prize.lottery.domain.agent.valobj;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AgentOperation {

    private Long      userId;
    private Long      income;
    private Integer   userAmt;
    private Long      withdraw;
    private Long      withRmb;
    private LocalDate withLatest;

    public static AgentOperation income(Long userId, Long income) {
        AgentOperation operation = new AgentOperation();
        operation.setUserId(userId);
        operation.setIncome(income);
        operation.setUserAmt(1);
        return operation;
    }

    public static AgentOperation withdraw(Long userId, Long withdraw, Long withRmb) {
        AgentOperation operation = new AgentOperation();
        operation.setUserId(userId);
        operation.setIncome(-withdraw);
        operation.setWithdraw(withdraw);
        operation.setWithRmb(withRmb);
        operation.setWithLatest(LocalDate.now());
        return operation;
    }

    public static AgentOperation withdrawRollback(Long userId, Long withdraw, Long withRmb) {
        AgentOperation operation = new AgentOperation();
        operation.setUserId(userId);
        operation.setIncome(withdraw);
        operation.setWithdraw(-withdraw);
        operation.setWithRmb(-withRmb);
        return operation;
    }

}
