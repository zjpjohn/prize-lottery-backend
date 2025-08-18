package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;

@TypeHandler(type = Type.ENUM)
public enum AgentApplyState implements Value<Integer> {
    CANCEL(0, "已取消"),
    APPLYING(1, "申请中"),
    ADOPTED(2, "已通过"),
    FAILED(3, "未通过");

    private final Integer value;
    private final String  remark;

    AgentApplyState(Integer value, String remark) {
        this.value  = value;
        this.remark = remark;
    }

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
