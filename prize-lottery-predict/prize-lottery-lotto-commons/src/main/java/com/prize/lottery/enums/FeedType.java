package com.prize.lottery.enums;

import com.cloud.arch.enums.Value;


public enum FeedType implements Value<Integer> {
    PREDICT(1, "专家推荐"),
    SKILL(2, "选彩技巧"),
    NEWS(3, "彩票资讯"),
    ;

    private final Integer type;
    private final String  remark;

    FeedType(Integer type, String remark) {
        this.type   = type;
        this.remark = remark;
    }

    @Override
    public Integer value() {
        return this.type;
    }

    @Override
    public String label() {
        return this.remark;
    }

}
