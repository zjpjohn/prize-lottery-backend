package com.prize.lottery.domain.agent.model;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.agent.valobj.AgentIncome;
import com.prize.lottery.domain.agent.valobj.AgentOperation;
import com.prize.lottery.domain.withdraw.specs.WithdrawRuleSpec;
import com.prize.lottery.domain.withdraw.specs.WithdrawValObj;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.AgentLevel;
import com.prize.lottery.infrast.persist.enums.UserState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AgentAcctDo {

    private Long       userId;
    private AgentLevel agent;
    private Long       income;
    private Integer    userAmt;
    private Long       withdraw;
    private Long       withRmb;
    private LocalDate  withLatest;
    private Integer    version;
    private UserState  state;

    //账户变动记录
    private AgentOperation operation;
    //分成收益记录
    private AgentIncome    agentIncome;

    public AgentAcctDo(Long userId) {
        this.userId = userId;
    }

    /**
     * 收益提现
     *
     * @param seqNo  收益编号
     * @param income 收益金额
     */
    public void income(String seqNo, Long userId, Long income, Double ratio, Integer channel) {
        //收益流水记录
        this.agentIncome = new AgentIncome(seqNo, this.userId, userId, income.intValue(), ratio, channel);
        //收益账户操作
        this.operation = AgentOperation.income(this.userId, income);
    }

    /**
     * 校验当前账户是否满足提现
     */
    public boolean canWithdraw(WithdrawRuleSpec rule) {
        return this.state == UserState.NORMAL && rule.canWithdraw(this.income);
    }

    /**
     * 收益提现
     *
     * @param ruleSpec 提现规则
     * @param withdraw 提现金额
     */
    public void withdraw(WithdrawRuleSpec ruleSpec, Long withdraw) {
        Assert.state(this.state == UserState.NORMAL, ResponseHandler.WITHDRAW_FORBIDDEN);
        Assert.state(this.income > withdraw, ResponseHandler.BALANCE_DEDUCT_ILLEGAL);
        Assert.state(ruleSpec.satisfy(withdraw, this.withLatest), ResponseHandler.WITHDRAW_ILLEGAL);
        this.operation = AgentOperation.withdraw(userId, withdraw, withdraw / 10);
    }

    /**
     * 收益提现回退
     *
     * @param valObj 提现信息
     */
    public void withdrawRollback(WithdrawValObj valObj) {
        this.operation = AgentOperation.withdrawRollback(this.userId, valObj.getWithdraw(), valObj.getWitRmb());
    }

}
