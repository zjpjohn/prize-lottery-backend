package com.prize.lottery.pay;

import com.cloud.arch.enums.Value;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TradeState implements Value<String> {
    SUCCESS("success", "支付成功"),
    CLOSED("closed", "交易关闭"),
    WAIT_PAY("wait_pay", "等待支付"),
    ;

    private final String state;
    private final String remark;

    @Override
    public String value() {
        return this.state;
    }

    @Override
    public String label() {
        return this.remark;
    }

}
