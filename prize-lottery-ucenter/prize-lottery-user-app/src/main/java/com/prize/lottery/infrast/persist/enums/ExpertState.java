package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum ExpertState implements Value<Integer> {

    INVALID(0, "无效"),
    APPLY(1, "申请中"),
    ADOPTED(2, "审核通过"),
    LOCKED(3, "已冻结");

    private final Integer state;
    private final String  name;

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


    public static ExpertState findOf(Integer state) {
        return Arrays.stream(values())
                     .filter(v -> v.getState().equals(state))
                     .findFirst()
                     .orElseThrow(ResponseHandler.ENUM_VALUE_UNKNOWN);
    }

}
