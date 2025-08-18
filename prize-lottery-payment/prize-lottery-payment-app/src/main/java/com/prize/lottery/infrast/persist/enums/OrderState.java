package com.prize.lottery.infrast.persist.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum OrderState implements Value<Integer> {
    FAIL(0, "支付失败"),
    WAIT_PAY(1, "待支付"),
    SUCCESS(2, "支付成功"),
    CLOSED(3, "订单关闭");

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
}
