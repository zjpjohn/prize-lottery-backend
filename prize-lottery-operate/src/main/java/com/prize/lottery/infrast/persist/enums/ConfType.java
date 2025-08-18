package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;

@TypeHandler(type = Type.ENUM)
public enum ConfType implements Value<Integer> {
    STRING(1, "string"),
    INT(2, "int"),
    LONG(3, "long"),
    DOUBLE(4, "double");

    private final Integer type;
    private final String  name;

    ConfType(Integer type, String name) {
        this.type = type;
        this.name = name;
    }


    @Override
    public Integer value() {
        return this.type;
    }

    @Override
    public String label() {
        return this.name;
    }

}
