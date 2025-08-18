package com.prize.lottery.event;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum MessageType implements Value<Integer> {
    TEXT(1, "文本"),
    LINK(2, "链接"),
    CARD(3, "卡片");

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

    public static MessageType of(Integer type) {
        return Arrays.stream(values()).filter(e -> Objects.equals(e.type, type)).findFirst().orElse(null);
    }
}
