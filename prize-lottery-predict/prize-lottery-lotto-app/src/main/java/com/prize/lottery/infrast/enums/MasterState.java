package com.prize.lottery.infrast.enums;


import com.cloud.arch.enums.Value;

public enum MasterState implements Value<Integer> {
    INVALID(0, "无效"),
    CREATING(1, "创建中"),
    NORMAL(2, "审核通过"),
    LOCKING(3, "冻结");

    private final Integer state;
    private final String  name;

    MasterState(Integer state, String name) {
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
