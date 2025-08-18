package com.prize.lottery.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum CodeType implements Value<Integer> {
    FOUR(1, "万能四码"),
    FIVE(2, "万能五码"),
    SIX(3, "万能六码"),
    SEVEN(4, "万能七码");

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
