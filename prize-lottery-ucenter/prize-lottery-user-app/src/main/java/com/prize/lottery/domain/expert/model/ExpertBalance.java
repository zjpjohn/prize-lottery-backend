package com.prize.lottery.domain.expert.model;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.expert.valobj.ExpertIncome;
import com.prize.lottery.domain.expert.valobj.ExpertOperation;
import com.prize.lottery.domain.withdraw.specs.WithdrawRuleSpec;
import com.prize.lottery.domain.withdraw.specs.WithdrawValObj;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.ExpertState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ExpertBalance {

    private Long        userId;
    private String      masterId;
    private Long        income;
    private Long        withdraw;
    private Long        withRmb;
    private LocalDate   withLatest;
    private ExpertState state;

    private ExpertIncome    expertIncome;
    private ExpertOperation operation;

    /**
     * 账户收益
     *
     * @param payId  支付金币标识
     * @param period 期号
     * @param type   彩种类型
     * @param income 收益金币
     */
    public void income(String seqNo, Long payId, String period, String type, Long income) {
        //账户操作
        this.operation = ExpertOperation.income(this.userId, income);
        //收益记录
        this.expertIncome = new ExpertIncome(seqNo, this.userId, payId, period, type, income);
    }

    /**
     * 校验当前账户是否满足提现
     */
    public boolean canWithdraw(WithdrawRuleSpec rule) {
        return this.state == ExpertState.ADOPTED && rule.canWithdraw(this.income);
    }

    /**
     * 提现请求
     *
     * @param withdraw 提现金币金额
     */
    public void withdraw(WithdrawRuleSpec rule, Long withdraw) {
        //提现规则校验
        Assert.state(this.state == ExpertState.ADOPTED, ResponseHandler.WITHDRAW_FORBIDDEN);
        Assert.state(this.income > withdraw, ResponseHandler.BALANCE_DEDUCT_ILLEGAL);
        Assert.state(rule.satisfy(this.withdraw, this.withLatest), ResponseHandler.WITHDRAW_ILLEGAL);
        //账户提现操作
        this.operation = ExpertOperation.withdraw(this.userId, withdraw, withdraw / 10);
    }

    /**
     * 提现回退
     *
     * @param valObj 提现信息
     */
    public void withdrawRollback(WithdrawValObj valObj) {
        Long withdraw = valObj.getWithdraw();
        //回退账户操作
        Long witRmb = valObj.getWitRmb();
        this.operation = ExpertOperation.rollback(this.userId, withdraw, witRmb);
    }

}
