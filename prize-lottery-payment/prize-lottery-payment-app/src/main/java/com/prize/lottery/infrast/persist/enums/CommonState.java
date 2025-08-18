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
            return Sets.newHashSet(USING, INVALID);
        }
    },
    USING(2, "使用中") {
        @Override
        public Set<CommonState> transitions() {
            return Sets.newHashSet(CREATED);
        }
    };

    private final Integer state;
    private final String  remark;

    @Override
    public Integer value() {
        return this.state;
    }

    @Override
    public String label() {
        return this.remark;
    }

    public abstract Set<CommonState> transitions();

}
