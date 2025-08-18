package com.prize.lottery.domain.user.model;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.user.valobj.BalanceOperation;
import com.prize.lottery.domain.withdraw.specs.WithdrawRuleSpec;
import com.prize.lottery.domain.withdraw.specs.WithdrawValObj;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.po.UserBalanceLogPo;
import com.prize.lottery.infrast.persist.po.UserBalancePo;
import com.prize.lottery.infrast.props.CouponExchangeProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@Data
@NoArgsConstructor
public class UserBalance {
    //非提现金币单次使用上限
    public static final Integer SURPLUS_THROTTLE = 20;

    private Long             userId;
    //提现账户金币余额
    private Integer          balance;
    //不可提现账户金币余额
    private Integer          surplus;
    //邀请奖励余额
    private Integer          invite;
    //积分余额
    private Integer          coupon;
    //代金券余额
    private Integer          voucher;
    //提现
    private Integer          withdraw;
    //提现换算成人民币
    private Integer          withRmb;
    //最近提现日期
    private LocalDate        withLatest;
    //是否可提现
    private Integer          canWith;
    //是否可以分润
    private Integer          canProfit;
    //数据版本号
    private Integer          version;
    //账户操作增量值对象
    private BalanceOperation operation;

    public UserBalance(Long userId) {
        this.userId  = userId;
        this.version = 0;
    }

    public boolean isNew() {
        return this.version == 0;
    }

    /**
     * 是否可以提现
     */
    public boolean canWithdraw(WithdrawRuleSpec rule) {
        return this.canWith == 1 && rule.canWithdraw(Long.valueOf(this.balance));
    }

    /**
     * 是否允许提现开关
     */
    public UserBalance switchCanWithdraw() {
        UserBalance userBalance = new UserBalance(this.userId);
        userBalance.setVersion(this.version);
        userBalance.setCanWith(this.canWith == 1 ? 0 : 1);
        return userBalance;
    }

    /**
     * 账户是否可以分润
     */
    public boolean canProfited() {
        return this.canProfit == 1;
    }

    /**
     * 是否允许账户分润开关
     */
    public UserBalance switchCanProfit() {
        UserBalance userBalance = new UserBalance(this.userId);
        userBalance.setVersion(this.version);
        userBalance.setCanProfit(this.canProfit == 1 ? 0 : 1);
        return userBalance;
    }

    /**
     * 将变动操作转换成数据对象
     */
    public Optional<UserBalancePo> toBalance() {
        return Optional.ofNullable(this.operation).map(operation -> operation.convert(this.userId));
    }

    /**
     * 将账户操作转换成操作日志
     */
    public Optional<UserBalanceLogPo> toBalanceLog() {
        return Optional.ofNullable(this.operation).map(operation -> operation.convertLog(this.userId));
    }

    /**
     * 广告激励增加账户奖励金
     *
     * @param balance 奖励金增量
     */
    public void incrBalance(int balance, int surplus) {
        this.operation = BalanceOperation.adsReward(balance, surplus);
    }

    /**
     * 邀请获得不可提现金币
     *
     * @param reward 金币增量
     */
    public void inviteSurplus(int reward) {
        this.operation = BalanceOperation.inviteReward(reward);
    }

    /**
     * 判断账户金额是否足够
     *
     * @param expend 消费金币
     */
    public boolean hasEnough(int expend) {
        int deductedSurplus = Math.min(this.surplus + this.voucher, SURPLUS_THROTTLE);
        return this.balance + deductedSurplus >= expend;
    }

    /**
     * 手动充值
     */
    public void manualCharge(Integer value) {
        boolean canCharge = this.canProfit == 0 && this.canWith == 0;
        Assert.state(canCharge, ResponseHandler.MANUAL_CHARGE_FORBIDDEN);
        this.operation = BalanceOperation.manualCharge(value);
    }

    /**
     * 扣减金币
     *
     * @param expend  待扣减总金额
     * @param surplus 待扣减非提现金币数量
     * @param voucher 已抵扣代金券数量
     * @return 抵扣可提现金币数量
     */
    public Integer deduct(int expend, int voucher, int surplus, String remark) {
        //扣减非提现金币数量
        int deductedSurplus = Math.min(this.surplus, surplus);
        //扣减提现金币数量
        int deductBounty = expend - deductedSurplus - voucher;
        this.balance = this.balance - deductBounty;
        this.voucher = this.voucher - voucher;
        this.surplus = this.surplus - deductedSurplus;
        //扣减操作日志
        this.operation = BalanceOperation.deduct(deductBounty, voucher, deductedSurplus, expend, remark);
        //返回扣减提现金币数量
        return deductBounty;
    }

    /**
     * 发放奖励积分
     */
    public void rewardCoupon(int coupon, String remark) {
        this.operation = BalanceOperation.rewardCoupon(coupon, remark);
    }

    /**
     * 判断积分是否满足兑换要求
     *
     * @param rule 积分兑换规则
     */
    public boolean canExchange(CouponExchangeProperties rule) {
        return this.coupon >= rule.getThrottle();
    }

    /**
     * 积分兑换
     */
    public void exchangeCoupon(CouponExchangeProperties rule) {
        //判断是否可以兑换
        Assert.state(this.canExchange(rule), ResponseHandler.COUPON_EXCHANGE_ILLEGAL);
        //可兑换的奖励金
        int surplus = this.coupon / rule.getRatio();
        //预计兑换消耗的积分
        int coupon = surplus * rule.getRatio();
        //当前账户的积分余额
        this.coupon    = this.coupon - coupon;
        this.operation = BalanceOperation.couponExchange(surplus, coupon);
    }

    /**
     * 领券计算
     *
     * @param voucher 代金券金额
     */
    public void drawVoucher(Integer voucher) {
        this.operation = BalanceOperation.voucherReward(voucher);
    }

    /**
     * 代金券过期销毁
     *
     * @param voucher 过期代金券金额
     */
    public void expireVoucher(Integer voucher) {
        this.operation = BalanceOperation.voucherRollback(voucher);
    }

    /**
     * 提现计算
     */
    public void withdraw(WithdrawRuleSpec rule, Long withdraw) {
        Assert.state(this.canWith == 1, ResponseHandler.WITHDRAW_FORBIDDEN);
        Assert.state(this.balance > withdraw, ResponseHandler.BALANCE_DEDUCT_ILLEGAL);
        Assert.state(rule.satisfy(withdraw, this.withLatest), ResponseHandler.WITHDRAW_ILLEGAL);
        //账户提现操作
        int withValue = withdraw.intValue();
        this.operation = BalanceOperation.withdraw(withValue, withValue / 10);
    }

    /**
     * 提现金额回退
     */
    public void withdrawRollback(WithdrawValObj valObj) {
        Long withdraw = valObj.getWithdraw();
        Long withRmb  = valObj.getWitRmb();
        this.operation = BalanceOperation.withdrawRollback(withdraw.intValue(), withRmb.intValue());
    }

}
