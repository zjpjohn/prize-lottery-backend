package com.prize.lottery.infrast.persist.enums;


import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;

@TypeHandler(type = Type.ENUM)
public enum ActionDirection implements Value<Integer> {
    PLUS(1, "+"),
    DEDUCT(2, "-") {
        @Override
        public Integer calc(Integer value) {
            return -1 * value;
        }
    };

    private final Integer direction;
    private final String  name;

    ActionDirection(Integer direction, String name) {
        this.direction = direction;
        this.name      = name;
    }

    public Integer getDirection() {
        return direction;
    }

    public String getName() {
        return name;
    }

    @Override
    public Integer value() {
        return this.direction;
    }

    /**
     * 枚举值描述
     */
    @Override
    public String label() {
        return name;
    }

    public Integer calc(Integer value) {
        return value;
    }

}
