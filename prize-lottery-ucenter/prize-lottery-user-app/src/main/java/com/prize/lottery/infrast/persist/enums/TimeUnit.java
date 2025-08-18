package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum TimeUnit implements Value<Integer> {
    WEEK(0, "周") {
        @Override
        public LocalDateTime expireAt(LocalDateTime time) {
            return time.plusWeeks(1);
        }
    },

    MONTH(1, "月") {
        @Override
        public LocalDateTime expireAt(LocalDateTime time) {
            return time.plusMonths(1);
        }
    },
    QUARTER(2, "季度") {
        @Override
        public LocalDateTime expireAt(LocalDateTime time) {
            return time.plusMonths(3);
        }
    },
    HALF_YEAR(3, "半年") {
        @Override
        public LocalDateTime expireAt(LocalDateTime time) {
            return time.plusMonths(6);
        }
    },
    YEAR(4, "年") {
        @Override
        public LocalDateTime expireAt(LocalDateTime time) {
            return time.plusYears(1);
        }
    },
    PERMANENT(5, "永久有效") {
        @Override
        public LocalDateTime expireAt(LocalDateTime time) {
            return time.plusYears(10);
        }
    };

    private final Integer unit;
    private final String  remark;

    @Override
    public Integer value() {
        return this.unit;
    }

    @Override
    public String label() {
        return this.remark;
    }

    public abstract LocalDateTime expireAt(LocalDateTime time);

    public static Optional<TimeUnit> of(Integer unit) {
        return Arrays.stream(values()).filter(e -> e.unit.equals(unit)).findFirst();
    }

}
