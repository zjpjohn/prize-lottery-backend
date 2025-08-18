package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;

@TypeHandler(type = Type.ENUM)
public enum AdminState implements Value<Integer> {
    INVALID(0, "无效"),
    NORMAL(1, "正常"),
    LOCKED(2, "锁定");

    private final String  name;
    private final Integer state;

    AdminState(Integer state, String name) {
        this.state = state;
        this.name  = name;
    }

    public Integer getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    /**
     * 获取枚举变量唯一值
     */
    @Override
    public Integer value() {
        return this.state;
    }

    /**
     * 枚举值描述
     */
    @Override
    public String label() {
        return name;
    }


}
