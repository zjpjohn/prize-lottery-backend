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
public enum TransferState implements Value<Integer> {
    WAIT_PAY(1, "WAIT_PAY", "待确认"),
    PROCESSING(2, "DEALING", "处理中"),
    SUCCESS(3, "SUCCESS", "成功"),
    FAIL(4, "FAIL", "失败");

    private final Integer state;
    private final String  alias;
    private final String  remark;

    @Override
    public Integer value() {
        return this.state;
    }

    @Override
    public String label() {
        return this.remark;
    }

    public static TransferState of(String value) {
        return Arrays.stream(values())
                     .filter(e -> e.name().equals(value) || e.alias.equals(value))
                     .findFirst()
                     .orElse(WAIT_PAY);
    }
}
