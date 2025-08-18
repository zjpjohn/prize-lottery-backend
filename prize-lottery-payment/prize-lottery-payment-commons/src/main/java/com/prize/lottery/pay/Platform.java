package com.prize.lottery.pay;

import com.cloud.arch.enums.Value;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Platform implements Value<String> {

    ANDROID("android", "安卓应用"),
    IOS("ios", "苹果应用");

    private String platform;
    private String desc;


    @Override
    public String value() {
        return this.platform;
    }

    @Override
    public String label() {
        return this.desc;
    }

}
