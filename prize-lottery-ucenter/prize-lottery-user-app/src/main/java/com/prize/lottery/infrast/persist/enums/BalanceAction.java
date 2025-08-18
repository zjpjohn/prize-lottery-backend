package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum BalanceAction implements Value<Integer> {

    DEDUCT_BALANCE(1, "金币扣减"),
    INVITE_SURPLUS(2, "邀请奖励"),
    VOUCHER_SURPLUS(3, "代金券奖励"),
    EXCHANGE_COUPON(4, "积分兑换"),
    ADVERT_AWARD(5, "广告激励"),
    WITHDRAW_BALANCE(6, "奖励金提现"),
    COUPON_REWARD(7, "积分奖励"),
    MANUAL_CHARGE(8, "手动充值"),
    ;

    private final Integer action;
    private final String  name;

    /**
     * 获取枚举变量唯一值
     */
    @Override
    public Integer value() {
        return action;
    }

    /**
     * 枚举值描述
     */
    @Override
    public String label() {
        return name;
    }

}
