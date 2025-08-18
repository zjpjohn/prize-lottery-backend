package com.prize.lottery.dto;


import com.cloud.arch.enums.Value;

import java.util.Arrays;

public enum SmsChannel implements Value<String> {
    /**
     * 登录
     */
    LOGIN("login"),
    /**
     * 注册
     */
    REGISTER("register"),
    /**
     * 重置密码
     */
    RESET_PWD("reset"),
    /**
     * 更新账户
     */
    MODIFY("modify"),
    /**
     * 提现
     */
    WITHDRAW("withdraw");

    private final String channel;

    SmsChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    @Override
    public String value() {
        return this.channel;
    }

    /**
     * 枚举值描述
     */
    @Override
    public String label() {
        return "";
    }


    public static SmsChannel channel(String channel) {
        return Arrays.stream(values()).filter(v -> v.channel.equals(channel)).findFirst().orElse(null);
    }

}
