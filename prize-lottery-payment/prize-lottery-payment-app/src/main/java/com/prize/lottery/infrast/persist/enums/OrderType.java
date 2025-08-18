package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum OrderType implements Value<Integer> {
    MEMBER(1, "会员订单"),
    CHARGE(2, "充值订单");

    private final Integer type;
    private final String  remark;

    @Override
    public Integer value() {
        return this.type;
    }

    @Override
    public String label() {
        return this.remark;
    }
}
