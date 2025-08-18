package com.prize.lottery.enums;

import com.cloud.arch.enums.Value;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IndexChannel implements Value<Integer> {
    DAN(1, "胆码指数"),
    KILL(2, "杀码指数"),
    COM(3, "组选指数");

    private final Integer channel;
    private final String  name;

    @Override
    public Integer value() {
        return this.channel;
    }

    @Override
    public String label() {
        return this.name;
    }

}
