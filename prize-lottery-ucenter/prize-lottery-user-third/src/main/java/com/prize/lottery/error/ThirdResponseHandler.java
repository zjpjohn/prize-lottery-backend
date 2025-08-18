package com.prize.lottery.error;

import com.cloud.arch.web.error.ErrorHandler;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ThirdResponseHandler implements ErrorHandler {
    THIRD_SERVER_ERROR(500, "第三方服务异常"),
    ALI_PAY_ERROR(500, "支付宝响应错误"),
    WX_PAY_ERROR(500, "微信响应错误");

    private final HttpStatus status;
    private final Integer    code;
    private final String     message;

    ThirdResponseHandler(Integer code, String message) {
        this(HttpStatus.OK, code, message);
    }

    ThirdResponseHandler(HttpStatus status, Integer code, String message) {
        this.status  = status;
        this.code    = code;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getError() {
        return this.message;
    }

}
