package com.prize.lottery.error;

import lombok.Getter;

@Getter
public class PayRequestException extends RuntimeException {

    private static final long serialVersionUID = 4934463445309893282L;

    //支付系统错误标识码
    public static final String SYSTEM_ERROR = "SYSTEM_ERROR";

    /**
     * 支付渠道
     */
    private final String source;
    /**
     * 错误码
     */
    private final String code;
    /**
     * 错误内容
     */
    private final String error;

    public PayRequestException(String source, String code, String error) {
        super(String.format("[%s] request error,%s:%s", source, code, error));
        this.source = source;
        this.code   = code;
        this.error  = error;
    }

    /**
     * 非支付系统错误时回调
     */
    public boolean isRollback() {
        return !SYSTEM_ERROR.equals(this.code);
    }

}
