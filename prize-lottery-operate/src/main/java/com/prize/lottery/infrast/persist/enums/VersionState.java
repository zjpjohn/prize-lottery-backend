package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;

@TypeHandler(type = Type.ENUM)
public enum VersionState implements Value<Integer> {
    OFF_LINE(0, "下线"),
    PRE_LINE(1, "预发"),
    ON_LINE(2, "上线"),
    MAINTAIN(3, "主推");

    private final String  name;
    private final Integer state;

    VersionState(Integer state, String name) {
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
        return state;
    }

    /**
     * 枚举值描述
     */
    @Override
    public String label() {
        return name;
    }


}
