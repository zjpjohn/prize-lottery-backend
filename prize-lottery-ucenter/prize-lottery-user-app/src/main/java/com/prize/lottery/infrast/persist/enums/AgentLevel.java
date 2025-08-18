package com.prize.lottery.infrast.persist.enums;


import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;

@TypeHandler(type = Type.ENUM)
public enum AgentLevel implements Value<Integer> {
    NORMAL(0, "普通分享"),
    AGENT_LEVEL_1(1, "一级流量主"),
    AGENT_LEVEL_2(2, "二级流量主"),
    AGENT_LEVEL_3(3, "三级流量主");

    private final Integer level;
    private final String  remark;

    AgentLevel(Integer level, String remark) {
        this.level  = level;
        this.remark = remark;
    }

    /**
     * 获取枚举变量唯一值
     */
    @Override
    public Integer value() {
        return level;
    }

    /**
     * 枚举值描述
     */
    @Override
    public String label() {
        return remark;
    }
}
