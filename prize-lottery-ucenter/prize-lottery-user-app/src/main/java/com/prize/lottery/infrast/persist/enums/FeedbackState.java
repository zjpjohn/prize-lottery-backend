package com.prize.lottery.infrast.persist.enums;


import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.cloud.arch.web.error.ApiBizException;

import java.util.Arrays;

@TypeHandler(type = Type.ENUM)
public enum FeedbackState implements Value<Integer> {

    CLOSED(0, "已关闭"),
    CREATE(1, "待处理"),
    HANDLED(2, "已处理"),
    ENHANCE(3, "增强改进");

    private final String  name;
    private final Integer state;

    FeedbackState(Integer state, String name) {
        this.state = state;
        this.name  = name;
    }

    public Integer getState() {
        return state;
    }

    public String getName() {
        return name;
    }


    /**
     * 获取枚举变量唯一值
     */
    @Override
    public Integer value() {
        return state;
    }

    /**
     * 枚举值描述
     */
    @Override
    public String label() {
        return name;
    }


    public static FeedbackState findOf(Integer state) {
        return Arrays.stream(values())
                     .filter(v -> v.getState().equals(state))
                     .findFirst()
                     .orElseThrow(() -> new ApiBizException(404, "反馈处理状态错误."));
    }
}
