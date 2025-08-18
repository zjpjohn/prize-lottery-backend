package com.prize.lottery.domain.user.valobj;

import com.cloud.arch.utils.IdWorker;
import com.prize.lottery.infrast.persist.enums.ActionDirection;
import com.prize.lottery.infrast.persist.enums.BalanceAction;
import com.prize.lottery.infrast.persist.po.UserBalanceLogPo;
import com.prize.lottery.infrast.persist.po.UserBalancePo;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BalanceOperation {

    //账户操作流水号
    private String          seq;
    //可提现账户金币
    private Integer         balance;
    //不可提现账户金币
    private Integer         surplus;
    //邀请奖励
    private Integer         invite;
    //积分
    private Integer         coupon;
    //代金券
    private Integer         voucher;
    //提现金额
    private Integer         withdraw;
    //提现人民币金额
    private Integer         withRmb;
    //账户提现时间
    private LocalDate       withLatest;
    //操作状态:0-回退,1-正常
    private Integer         state = 1;
    //变动来源数量
    private Integer         source;
    //账户操作备注
    private String          remark;
    //账户变动方向
    private ActionDirection direct;
    //操作类型
    private BalanceAction   action;

    public BalanceOperation() {
        this.seq = String.valueOf(IdWorker.nextId());
    }

    /**
     * 将本次操作转换成账户变动对象
     *
     * @param userId 用户标识
     */
    public UserBalancePo convert(Long userId) {
        UserBalancePo balance = new UserBalancePo();
        balance.setUserId(userId);
        if (this.balance != null) {
            balance.setBalance(direct.calc(this.balance));
        }
        if (this.surplus != null) {
            balance.setSurplus(direct.calc(this.surplus));
        }
        if (this.invite != null) {
            balance.setInvite(direct.calc(this.surplus));
        }
        if (this.voucher != null) {
            balance.setVoucher(direct.calc(this.voucher));
        }
        if (this.coupon != null) {
            //判断是获得积分(1)还是积分兑换(-1)
            int factor = this.surplus == null ? 1 : -1;
            balance.setCoupon(factor * direct.calc(this.coupon));
        }
        if (this.withdraw != null && this.withRmb != null) {
            balance.setWithdraw(-1 * direct.calc(this.withdraw));
            balance.setWithRmb(-1 * direct.calc(this.withRmb));
        }
        if (this.withLatest != null) {
            balance.setWithLatest(this.withLatest);
        }
        return balance;
    }

    /**
     * 将账户操作转换成操作日志
     *
     * @param userId 用户标识
     */
    public UserBalanceLogPo convertLog(Long userId) {
        UserBalanceLogPo balanceLog = new UserBalanceLogPo();
        balanceLog.setUserId(userId);
        balanceLog.setSeq(this.seq);
        balanceLog.setDirect(this.direct);
        balanceLog.setBalance(this.balance);
        balanceLog.setSurplus(this.surplus);
        balanceLog.setVoucher(this.voucher);
        balanceLog.setSource(this.source);
        balanceLog.setAction(this.action);
        balanceLog.setRemark(this.remark);
        balanceLog.setState(this.state);
        return balanceLog;
    }

    public static BalanceOperation manualCharge(int balance) {
        BalanceOperation operation = new BalanceOperation();
        operation.setBalance(balance);
        operation.setSource(balance);
        operation.setRemark("后台手动充值");
        operation.setDirect(ActionDirection.PLUS);
        operation.setAction(BalanceAction.MANUAL_CHARGE);
        return operation;
    }

    /**
     * 发放积分奖励
     *
     * @param coupon 奖励积分
     */
    public static BalanceOperation rewardCoupon(int coupon, String remark) {
        BalanceOperation operation = new BalanceOperation();
        operation.setCoupon(coupon);
        operation.setSource(coupon);
        operation.setRemark(remark);
        operation.setDirect(ActionDirection.PLUS);
        operation.setAction(BalanceAction.MANUAL_CHARGE);
        return operation;
    }

    /**
     * 激励视频广告奖励
     *
     * @param balance 奖励金
     * @param surplus 金币
     */
    public static BalanceOperation adsReward(int balance, int surplus) {
        BalanceOperation operation = new BalanceOperation();
        operation.setBalance(balance);
        operation.setSurplus(surplus);
        operation.setSource(balance + surplus);
        operation.setRemark("观看激励视频广告收益");
        operation.setDirect(ActionDirection.PLUS);
        operation.setAction(BalanceAction.ADVERT_AWARD);
        return operation;
    }

    /**
     * 获取代金券
     *
     * @param voucher 代金券
     */
    public static BalanceOperation voucherReward(Integer voucher) {
        BalanceOperation operation = new BalanceOperation();
        operation.setVoucher(voucher);
        operation.setSource(voucher);
        operation.setRemark("领取代金券奖励");
        operation.setDirect(ActionDirection.PLUS);
        operation.setAction(BalanceAction.VOUCHER_SURPLUS);
        return operation;
    }

    /**
     * 代金券过期回退
     *
     * @param voucher 代金券金额
     */
    public static BalanceOperation voucherRollback(Integer voucher) {
        BalanceOperation operation = new BalanceOperation();
        operation.setVoucher(voucher);
        operation.setSource(voucher);
        operation.setRemark("代金券过期销毁");
        operation.setDirect(ActionDirection.DEDUCT);
        operation.setAction(BalanceAction.VOUCHER_SURPLUS);
        operation.setState(0);
        return operation;
    }

    /**
     * 邀请获得金币
     *
     * @param reward 金币
     */
    public static BalanceOperation inviteReward(Integer reward) {
        BalanceOperation operation = new BalanceOperation();
        operation.setSurplus(reward);
        operation.setInvite(reward);
        operation.setSource(reward);
        operation.setRemark("邀请用户获得金币");
        operation.setDirect(ActionDirection.PLUS);
        operation.setAction(BalanceAction.INVITE_SURPLUS);
        return operation;
    }

    /**
     * 扣减账户余额操作
     *
     * @param balance 奖励金
     * @param voucher 代金券
     * @param surplus 金币
     * @param remark  操作描述
     */
    public static BalanceOperation deduct(Integer balance, Integer voucher, Integer surplus, int deduct,
        String remark) {
        BalanceOperation operation = new BalanceOperation();
        operation.setBalance(balance);
        operation.setVoucher(voucher);
        operation.setSurplus(surplus);
        operation.setSource(deduct);
        operation.setRemark(remark);
        operation.setDirect(ActionDirection.DEDUCT);
        operation.setAction(BalanceAction.DEDUCT_BALANCE);
        return operation;
    }

    /**
     * 积分兑换账户金币
     *
     * @param surplus 待获得金币
     * @param coupon  待兑换积分
     */
    public static BalanceOperation couponExchange(Integer surplus, Integer coupon) {
        BalanceOperation operation = new BalanceOperation();
        operation.setSurplus(surplus);
        operation.setCoupon(coupon);
        operation.setSource(coupon);
        operation.setRemark("积分兑换账户金币");
        operation.setDirect(ActionDirection.PLUS);
        operation.setAction(BalanceAction.EXCHANGE_COUPON);
        return operation;
    }

    /**
     * 账户奖励金提现
     *
     * @param withdraw 待提现奖励金
     * @param withRmb  待提现换算后人民币金额
     */
    public static BalanceOperation withdraw(Integer withdraw, Integer withRmb) {
        BalanceOperation operation = new BalanceOperation();
        operation.setBalance(withdraw);
        operation.setWithdraw(withdraw);
        operation.setWithRmb(withRmb);
        operation.setSource(withdraw);
        operation.setRemark("账户奖励金提现");
        operation.setWithLatest(LocalDate.now());
        operation.setDirect(ActionDirection.DEDUCT);
        operation.setAction(BalanceAction.WITHDRAW_BALANCE);
        return operation;
    }

    /**
     * 账户提现金额回退
     *
     * @param withdraw 提现金额
     * @param withRmb  换算成人民币金额
     */
    public static BalanceOperation withdrawRollback(Integer withdraw, Integer withRmb) {
        BalanceOperation operation = new BalanceOperation();
        operation.setBalance(withdraw);
        operation.setWithdraw(withdraw);
        operation.setWithRmb(withRmb);
        operation.setSource(withdraw);
        operation.setState(0);//账户金额回退
        operation.setRemark("账户提现金额回退");
        operation.setDirect(ActionDirection.PLUS);
        operation.setAction(BalanceAction.WITHDRAW_BALANCE);
        return operation;
    }

}
