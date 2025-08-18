package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;

@TypeHandler(type = Type.ENUM)
public enum OpenType implements Value<Integer> {
    NONE(0, "无跳转"),
    APPLICATION(1, "打开应用"),
    ACTIVITY(2, "打开Activity"),
    URL(3, "打开URL");

    private final Integer type;
    private final String  name;

    OpenType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public Integer value() {
        return this.type;
    }

    @Override
    public String label() {
        return this.name;
    }

}
