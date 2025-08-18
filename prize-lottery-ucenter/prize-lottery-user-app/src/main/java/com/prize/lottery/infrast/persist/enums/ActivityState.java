package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum ActivityState implements Value<Integer> {
    INVALID(0, "无效") {
        @Override
        public Set<ActivityState> transitions() {
            return Sets.newHashSet(CREATED);
        }
    },
    CREATED(1, "已创建") {
        @Override
        public Set<ActivityState> transitions() {
            return Sets.newHashSet(INVALID, USING);
        }
    },
    USING(2, "投放中") {
        @Override
        public Set<ActivityState> transitions() {
            return Sets.newHashSet(CREATED);
        }
    };

    private final Integer state;
    private final String  remark;

    public abstract Set<ActivityState> transitions();

    @Override
    public Integer value() {
        return this.state;
    }

    @Override
    public String label() {
        return this.remark;
    }

}
