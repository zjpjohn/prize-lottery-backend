package com.prize.lottery.enums;


import com.cloud.arch.enums.Value;

import java.util.Arrays;
import java.util.Objects;

public enum WarningHit implements Value<Integer> {
    NONE_HIT(0, "未命中"),
    BAOZI_HIT(1, "豹子命中"),
    ZU3_HIT(2, "组三命中"),
    ZU6_HIT(3, "组六命中");

    private final Integer hit;
    private final String  name;

    WarningHit(Integer hit, String name) {
        this.hit  = hit;
        this.name = name;
    }

    @Override
    public Integer value() {
        return this.hit;
    }

    @Override
    public String label() {
        return this.name;
    }

    public static WarningHit of(Integer hit) {
        return Arrays.stream(values()).filter(v -> Objects.equals(v.hit, hit)).findFirst().orElse(WarningHit.NONE_HIT);
    }
}
