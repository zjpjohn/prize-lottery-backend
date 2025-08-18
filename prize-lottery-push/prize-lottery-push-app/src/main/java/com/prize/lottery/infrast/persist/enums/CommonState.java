package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.google.common.collect.Sets;

import java.util.Set;

@TypeHandler(type = Type.ENUM)
public enum CommonState implements Value<Integer> {

    INVALID(0, "无效") {
        @Override
        public Set<CommonState> transitions() {
            return Sets.newHashSet(CREATED);
        }
    },
    CREATED(1, "已创建") {
        @Override
        public Set<CommonState> transitions() {
            return Sets.newHashSet(INVALID, USING);
        }
    },
    USING(2, "使用中") {
        @Override
        public Set<CommonState> transitions() {
            return Sets.newHashSet(CREATED);
        }
    };

    private final Integer state;
    private final String  name;

    CommonState(Integer state, String name) {
        this.state = state;
        this.name  = name;
    }

    @Override
    public String label() {
        return this.name;
    }

    @Override
    public Integer value() {
        return this.state;
    }

    public abstract Set<CommonState> transitions();
}
