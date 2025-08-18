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
public enum PayChannel implements Value<String> {
    ALI_PAY("支付宝", "ali_pay"),
    WX_PAY("微信", "wx_pay"),
    SYS_PAY("后台付款", "sys_pay"),
    ;

    private final String name;
    private final String channel;

    @Override
    public String value() {
        return this.channel;
    }

    /**
     * 枚举值描述
     */
    @Override
    public String label() {
        return name;
    }

    public static PayChannel of(String channel) {
        return Arrays.stream(values())
                     .filter(v -> v.channel.equals(channel))
                     .findFirst()
                     .orElseThrow(ResponseHandler.ENUM_VALUE_UNKNOWN);
    }

}
