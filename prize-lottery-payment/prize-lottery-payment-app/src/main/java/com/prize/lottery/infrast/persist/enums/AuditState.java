package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum AuditState implements Value<Integer> {
    NONE(0, "无需审核"),
    PROCESSING(1, "审核中"),
    ADOPTED(2, "审核通过"),
    REJECTED(3, "拒绝转账");

    private final Integer state;
    private final String  remark;

    @Override
    public Integer value() {
        return this.state;
    }

    @Override
    public String label() {
        return this.remark;
    }

    public static AuditState of(Integer state) {
        return Arrays.stream(values())
                     .filter(v -> Objects.equals(v.state, state))
                     .findFirst()
                     .orElseThrow(ResponseHandler.DATA_STATE_ILLEGAL);
    }
}
