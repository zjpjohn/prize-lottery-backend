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
public enum WithdrawState implements Value<Integer> {
    REJECT(0, "拒绝提现"),
    APPLY(1, "发起提现"),
    ADOPTED(2, "审核通过"),
    FAILURE(3, "提现失败"),
    SUCCESS(4, "提现成功"),
    ;

    private final Integer state;
    private final String  name;

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


    public static WithdrawState ofValue(Integer state) {
        return Arrays.stream(values())
                     .filter(v -> v.getState().equals(state))
                     .findFirst()
                     .orElseThrow(ResponseHandler.ENUM_VALUE_UNKNOWN);
    }
}
