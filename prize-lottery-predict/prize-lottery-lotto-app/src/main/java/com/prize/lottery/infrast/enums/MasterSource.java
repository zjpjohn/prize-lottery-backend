package com.prize.lottery.infrast.enums;


import com.cloud.arch.enums.Value;

public enum MasterSource implements Value<Integer> {
    ICAI(1, "系统"),
    OWN(2, "自有"),
    ;

    private final Integer source;
    private final String  name;

    MasterSource(Integer source, String name) {
        this.source = source;
        this.name   = name;
    }

    public Integer getSource() {
        return source;
    }

    public String getName() {
        return name;
    }


    /**
     * 获取枚举变量唯一值
     */
    @Override
    public Integer value() {
        return this.source;
    }

    /**
     * 枚举值描述
     */
    @Override
    public String label() {
        return name;
    }


}
