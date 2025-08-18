package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum VoucherExpire implements Value<Integer> {
    NONE(0, "无过期"),
    IN_VALID(1, "有效期内"),
    EXPIRED(2, "已过期计算");

    private final Integer expired;
    private final String  remark;

    @Override
    public Integer value() {
        return this.expired;
    }

    @Override
    public String label() {
        return this.remark;
    }
}
