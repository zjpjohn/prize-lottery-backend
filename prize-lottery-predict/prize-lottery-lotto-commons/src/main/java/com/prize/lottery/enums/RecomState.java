package com.prize.lottery.enums;

import com.cloud.arch.enums.Value;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecomState implements Value<Integer> {
    CREATED(1, "已创建"),
    ISSUED(2, "已发布"),
    OPENED(3, "已开奖");

    private final Integer state;
    private final String  name;

    @Override
    public Integer value() {
        return this.state;
    }

    @Override
    public String label() {
        return this.name;
    }

}
