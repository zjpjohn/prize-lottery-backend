package com.prize.lottery.enums;


import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;

@TypeHandler(type = Type.ENUM)
public enum WarningEnums implements Value<Integer> {
    SUM_WARNING(1, "和值预警"),
    TAIL_WARNING(2, "和尾预警"),
    KUA_WARNING(3, "跨度预警"),
    DAN_WARNING(4, "胆码预警"),
    KILL_WARNING(5, "杀码预警");

    private final Integer type;
    private final String  remark;

    WarningEnums(Integer type, String remark) {
        this.type   = type;
        this.remark = remark;
    }

    /**
     * 获取枚举变量唯一值
     */
    @Override
    public Integer value() {
        return type;
    }

    /**
     * 枚举值描述
     */
    @Override
    public String label() {
        return remark;
    }

}
