package com.prize.lottery.infrast.persist.enums;


import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.google.common.collect.Sets;

import java.util.Set;

@TypeHandler(type = Type.ENUM)
public enum AgentRuleState implements Value<Integer> {

    CANCEL(0, "无效/删除") {
        @Override
        public Set<AgentRuleState> transitions() {
            return Sets.newHashSet(CREATED);
        }
    },
    CREATED(1, "已创建") {
        @Override
        public Set<AgentRuleState> transitions() {
            return Sets.newHashSet(PRE_START, USING, CANCEL);
        }
    },
    PRE_START(2, "预启用") {
        @Override
        public Set<AgentRuleState> transitions() {
            return Sets.newHashSet(CREATED, USING);
        }
    },
    USING(3, "使用中") {
        @Override
        public Set<AgentRuleState> transitions() {
            return Sets.newHashSet();
        }
    };

    private final Integer state;
    private final String  remark;

    AgentRuleState(Integer state, String remark) {
        this.state  = state;
        this.remark = remark;
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

    public abstract Set<AgentRuleState> transitions();
}
