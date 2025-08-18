package com.prize.lottery.infrast.persist.enums;


import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;

import java.util.Arrays;

@TypeHandler(type = Type.ENUM)
public enum PushState implements Value<Integer> {
    FAILED(0, "失败"),
    CREATED(1, "已创建"),
    WAITING(2, "等待中"),
    SENT(3, "已推送"),
    CANCELED(4, "已取消");

    private final Integer state;
    private final String  name;

    PushState(Integer state, String name) {
        this.state = state;
        this.name  = name;
    }

    @Override
    public Integer value() {
        return this.state;
    }

    @Override
    public String label() {
        return this.name;
    }

    public static PushState of(String name) {
        return Arrays.stream(values()).filter(e -> e.name.equals(name)).findFirst().orElse(null);
    }

}
