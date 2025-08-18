package com.prize.lottery.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum HitType implements Value<Integer> {
    UN_OPEN(0, "未开奖"),
    HIT_SAME(1, "豹子命中"),
    HIT_ZU3(2, "组三命中"),
    HIT_ZU6(3, "组六命中"),
    UN_HIT(4, "未命中");

    private final Integer hit;
    private final String  name;

    @Override
    public Integer value() {
        return this.hit;
    }

    @Override
    public String label() {
        return this.name;
    }

}
