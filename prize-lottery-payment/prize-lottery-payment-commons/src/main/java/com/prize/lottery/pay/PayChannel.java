package com.prize.lottery.pay;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
@TypeHandler(type = Type.ENUM)
public enum PayChannel implements Value<String> {
    ALI_PAY("ali_pay", "支付宝", false),
    WX_PAY("wx_pay", "微信支付", true),
    SYS_PAY("sys_pay", "后台转账", false),
    ;

    private final String  channel;
    private final String  remark;
    private final boolean batched;

    @Override
    public String value() {
        return this.channel;
    }

    @Override
    public String label() {
        return this.remark;
    }

    public static Optional<PayChannel> of(String channel) {
        return Arrays.stream(values()).filter(e -> e.channel.equals(channel)).findFirst();
    }

}
