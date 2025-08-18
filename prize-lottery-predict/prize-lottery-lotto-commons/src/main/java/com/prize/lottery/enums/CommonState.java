package com.prize.lottery.enums;


import com.cloud.arch.enums.Value;

public enum CommonState implements Value<Integer> {

    INVALID(0, "无效"),
    CREATED(1, "已创建"),
    ISSUED(2, "已发布");

    CommonState(Integer value, String remark) {
        this.value  = value;
        this.remark = remark;
    }

    private final Integer value;
    private final String  remark;

    /**
     * 获取枚举变量唯一值
     */
    @Override
    public Integer value() {
        return value;
    }

    /**
     * 枚举值描述
     */
    @Override
    public String label() {
        return remark;
    }
}
