package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.Set;


@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum VoucherState implements Value<Integer> {
    CANCEL(0, "已删除") {
        @Override
        public Set<VoucherState> transitions() {
            return Sets.newHashSet(CREATED);
        }
    },
    CREATED(1, "已创建") {
        @Override
        public Set<VoucherState> transitions() {
            return Sets.newHashSet(CANCEL, USING);
        }
    },
    USING(2, "投放中") {
        @Override
        public Set<VoucherState> transitions() {
            return Sets.newHashSet(OFF_SHELF);
        }
    },
    OFF_SHELF(3, "已下架") {
        @Override
        public Set<VoucherState> transitions() {
            return Sets.newHashSet();
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

    public boolean isUsing() {
        return Objects.equals(this.state, USING.getState());
    }

    public abstract Set<VoucherState> transitions();

}
