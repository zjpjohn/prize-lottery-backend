package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum PutState implements Value<Integer> {
    INVALID(0, "无效"),
    CREATED(1, "已创建") {
        @Override
        public Set<PutState> transitions() {
            return Sets.newHashSet(INVALID, PUTTING);
        }
    },
    PUTTING(2, "投放中") {
        @Override
        public Set<PutState> transitions() {
            return Sets.newHashSet(CREATED, OFFLINE);
        }
    },
    OFFLINE(3, "已下线");

    private final Integer state;
    private final String  remark;

    public Set<PutState> transitions() {
        return Sets.newHashSet();
    }

    @Override
    public Integer value() {
        return this.state;
    }

    @Override
    public String label() {
        return this.remark;
    }
}
