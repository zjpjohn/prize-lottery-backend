package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;

@TypeHandler(type = Type.ENUM)
public enum AdvertState implements Value<Integer> {
    CLOSED(0, "关闭"),
    CREATED(1, "已创建"),
    COMPLETED(2, "完成");

    private final String  name;
    private final Integer state;

    AdvertState(Integer state, String name) {
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
