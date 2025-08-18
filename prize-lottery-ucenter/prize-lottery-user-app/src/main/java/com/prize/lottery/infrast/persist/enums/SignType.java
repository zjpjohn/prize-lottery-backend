package com.prize.lottery.infrast.persist.enums;


import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;

@TypeHandler(type = Type.ENUM)
public enum SignType implements Value<Integer> {
    EVERY(1, "每日签到"),
    SERIES(2, "连续签到");

    private final Integer type;
    private final String  name;

    SignType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }


    /**
     * 获取枚举变量唯一值
     */
    @Override
    public Integer value() {
        return this.type;
    }

    /**
     * 枚举值描述
     */
    @Override
    public String label() {
        return name;
    }

}
