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
public enum WithdrawRuleState implements Value<Integer> {
    INVALID("无效", 0) {
        @Override
        public Set<WithdrawRuleState> transitions() {
            return Sets.newHashSet(CREATED);
        }
    },
    CREATED("已创建", 1) {
        @Override
        public Set<WithdrawRuleState> transitions() {
            return Sets.newHashSet(PRE_ISSUED, ISSUED, INVALID);
        }
    },
    PRE_ISSUED("预发布", 2) {
        @Override
        public Set<WithdrawRuleState> transitions() {
            return Sets.newHashSet(CREATED, ISSUED);
        }
    },
    ISSUED("已发布", 3) {
        @Override
        public Set<WithdrawRuleState> transitions() {
            return Sets.newHashSet();
        }
    };

    private final String  name;
    private final Integer state;

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

    public abstract Set<WithdrawRuleState> transitions();

}
