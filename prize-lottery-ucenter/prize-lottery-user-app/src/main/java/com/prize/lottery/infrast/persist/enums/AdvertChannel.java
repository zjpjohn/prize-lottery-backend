package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.cloud.arch.web.error.ApiBizException;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

@TypeHandler(type = Type.ENUM)
public enum AdvertChannel implements Value<Integer> {
    TENCENT_AD("腾讯广告", 1),
    BYTE_AD("字节广告", 2);

    private final String  name;
    private final Integer channel;

    AdvertChannel(String name, Integer channel) {
        this.name    = name;
        this.channel = channel;
    }

    public String getName() {
        return name;
    }

    public Integer getChannel() {
        return channel;
    }

    /**
     * 获取枚举变量唯一值
     */
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


    @JsonCreator
    public static AdvertChannel findOf(Integer channel) {
        return Arrays.stream(values())
                     .filter(v -> v.getChannel().equals(channel))
                     .findFirst()
                     .orElseThrow(() -> new ApiBizException(400, "广告渠道错误"));
    }
}
