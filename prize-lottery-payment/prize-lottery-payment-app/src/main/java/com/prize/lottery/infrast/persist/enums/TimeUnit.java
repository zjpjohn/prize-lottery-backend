package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum TimeUnit implements Value<Integer> {
    WEEK(0, "周") {
        @Override
        public Long expireDays(LocalDateTime time) {
            LocalDateTime expireTime = time.plusWeeks(1);
            return time.until(expireTime, ChronoUnit.DAYS);
        }
    },

    MONTH(1, "月") {
        @Override
        public Long expireDays(LocalDateTime time) {
            LocalDateTime expireTime = time.plusMonths(1);
            return time.until(expireTime, ChronoUnit.DAYS);
        }
    },
    QUARTER(2, "季度") {
        @Override
        public Long expireDays(LocalDateTime time) {
            LocalDateTime expireTime = time.plusMonths(3);
            return time.until(expireTime, ChronoUnit.DAYS);
        }
    },
    HALF_YEAR(3, "半年") {
        @Override
        public Long expireDays(LocalDateTime time) {
            LocalDateTime expireTime = time.plusMonths(6);
            return time.until(expireTime, ChronoUnit.DAYS);
        }
    },
    YEAR(4, "年") {
        @Override
        public Long expireDays(LocalDateTime time) {
            LocalDateTime expireTime = time.plusYears(1);
            return time.until(expireTime, ChronoUnit.DAYS);
        }
    },
    PERMANENT(5, "永久有效") {
        @Override
        public Long expireDays(LocalDateTime time) {
            LocalDateTime expireTime = time.plusYears(10);
            return time.until(expireTime, ChronoUnit.DAYS);
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

    public abstract Long expireDays(LocalDateTime time);

    public static TimeUnit of(Integer unit) {
        return Arrays.stream(values())
                     .filter(e -> e.unit.equals(unit))
                     .findFirst()
                     .orElseThrow(ResponseHandler.TIME_UNIT_ERROR);
    }
}
