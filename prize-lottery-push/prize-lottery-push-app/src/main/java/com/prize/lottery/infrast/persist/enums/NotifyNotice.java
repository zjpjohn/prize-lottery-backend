package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;

@TypeHandler(type = Type.ENUM)
public enum NotifyNotice implements Value<Integer> {
    NONE(0, "不作处理"),
    BOTH(1, "震动和声音"),
    VIBRATE(2, "震动"),
    SOUND(3, "声音");

    private final Integer value;
    private final String  name;

    NotifyNotice(Integer value, String name) {
        this.value = value;
        this.name  = name;
    }

    @Override
    public Integer value() {
        return this.value;
    }

    @Override
    public String label() {
        return this.name;
    }

}
