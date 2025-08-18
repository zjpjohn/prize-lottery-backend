package com.prize.lottery.enums;


import com.cloud.arch.enums.Value;
import lombok.Getter;

@Getter
public enum MasterChannel implements Value<Integer> {

    ICAI(1, "icai专家"),
    OWN(2, "自有专家");

    private final Integer channel;
    private final String  name;

    MasterChannel(Integer channel, String name) {
        this.channel = channel;
        this.name    = name;
    }

    @Override
    public Integer value() {
        return this.channel;
    }

    /**
     * 枚举值描述
     */
    @Override
    public String label() {
        return name;
    }


}
