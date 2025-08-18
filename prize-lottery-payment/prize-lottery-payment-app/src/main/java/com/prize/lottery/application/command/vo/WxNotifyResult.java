package com.prize.lottery.application.command.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class WxNotifyResult extends ResponseEntity<WxNotifyResult.ResultBody> {

    public static final String FAIL    = "FAIL";
    public static final String SUCCESS = "SUCCESS";

    private final ResultBody content;

    public WxNotifyResult(HttpStatus status, String code, String message) {
        super(status);
        this.content = new ResultBody(code, message);
    }

    @Override
    public ResultBody getBody() {
        return this.content;
    }

    public static WxNotifyResult success() {
        return new WxNotifyResult(HttpStatus.OK, SUCCESS, null);
    }

    public static WxNotifyResult fail(HttpStatus status, String message) {
        return new WxNotifyResult(status, FAIL, message);
    }

    public static WxNotifyResult badRequest(String message) {
        return fail(HttpStatus.BAD_REQUEST, message);
    }

    public static WxNotifyResult serverError() {
        return fail(HttpStatus.INTERNAL_SERVER_ERROR, "回调处理错误");
    }

    @Getter
    @AllArgsConstructor
    public static class ResultBody {

        private final String code;
        private final String message;
    }

}
