package com.prize.lottery.infrast.persist.enums;


import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;

@TypeHandler(type = Type.ENUM)
public enum ResourceState implements Value<Integer> {
    INVALID(0, "已废弃"),
    CREATED(1, "未使用"),
    USING(2, "已使用");

    private final Integer state;
    private final String  remark;

    ResourceState(Integer state, String remark) {
        this.state  = state;
        this.remark = remark;
    }

    public Integer getState() {
        return state;
    }

    public String getRemark() {
        return remark;
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
        return remark;
    }
}
