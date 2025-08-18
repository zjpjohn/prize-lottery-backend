package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 优惠券适用范围
 */
@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum VoucherScope implements Value<Integer> {
    ALL_SCOPE(1, "通用券"),
    NEW_SCOPE(2, "新人券"),
    DIRECT_SCOPE(3, "定向券"),
    FESTIVAL_SCOPE(4, "节日全");

    private final Integer scope;
    private final String  remark;

    @Override
    public Integer value() {
        return this.scope;
    }

    @Override
    public String label() {
        return this.remark;
    }
}
