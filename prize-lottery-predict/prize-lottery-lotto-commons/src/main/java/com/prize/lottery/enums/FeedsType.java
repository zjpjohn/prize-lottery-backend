package com.prize.lottery.enums;


import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;

@TypeHandler(type = Type.ENUM)
public enum FeedsType implements Value<Integer> {
    DAN(1, "胆码"),
    KILL(2, "杀码"),
    COM(3, "组码"),
    BLUE(4, "蓝球"),
    ;

    private final Integer type;
    private final String  remark;

    FeedsType(Integer type, String remark) {
        this.type   = type;
        this.remark = remark;
    }

    @Override
    public String label() {
        return this.remark;
    }

    @Override
    public Integer value() {
        return this.type;
    }

}
