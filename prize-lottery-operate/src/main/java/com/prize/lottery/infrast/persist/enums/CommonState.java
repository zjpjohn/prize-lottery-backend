package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.cloud.arch.web.converter.ConvertParseException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.Set;

@TypeHandler(type = Type.ENUM)
public enum CommonState implements Value<Integer> {
    INVALID(0, "无效") {
        @Override
        public Set<CommonState> transitions() {
            return Sets.newHashSet(CommonState.CREATED);
        }
    },
    CREATED(1, "已创建") {
        @Override
        public Set<CommonState> transitions() {
            return Sets.newHashSet(CommonState.INVALID, CommonState.USING);
        }
    },
    USING(2, "使用中") {
        @Override
        public Set<CommonState> transitions() {
            return Sets.newHashSet(CommonState.CREATED);
        }
    };

    private final Integer state;
    private final String  remark;

    CommonState(Integer state, String remark) {
        this.state  = state;
        this.remark = remark;
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
        return this.remark;
    }

    public abstract Set<CommonState> transitions();

    @JsonCreator
    public static CommonState of(Integer state) {
        return Arrays.stream(values())
                     .filter(e -> e.value().equals(state))
                     .findFirst()
                     .orElseThrow(() -> new ConvertParseException("未知状态，状态仅支持[0,1,2]"));
    }

}
