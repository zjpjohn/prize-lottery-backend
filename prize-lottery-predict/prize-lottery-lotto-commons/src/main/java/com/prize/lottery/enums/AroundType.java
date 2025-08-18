package com.prize.lottery.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum AroundType implements Value<Integer> {
    KAI(1, "开奖号"),
    SHI(2, "试机号"),
    LOTTO(3, "开机号");

    private final Integer type;
    private final String  name;

    @Override
    public Integer value() {
        return this.type;
    }

    @Override
    public String label() {
        return this.name;
    }

}
