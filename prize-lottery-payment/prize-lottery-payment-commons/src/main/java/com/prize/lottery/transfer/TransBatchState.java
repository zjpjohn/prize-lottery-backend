package com.prize.lottery.transfer;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum TransBatchState implements Value<Integer> {

    CLOSED(0, "已关闭"),
    ACCEPTED(1, "已受理"),
    PROCESSING(2, "转账中"),
    FINISHED(3, "已完成");

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

    public static TransBatchState of(String value) {
        return Arrays.stream(values()).filter(e -> e.name().equals(value)).findFirst().orElse(ACCEPTED);
    }

}
