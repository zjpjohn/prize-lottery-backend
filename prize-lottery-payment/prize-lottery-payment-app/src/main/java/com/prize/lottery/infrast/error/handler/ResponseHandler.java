package com.prize.lottery.infrast.error.handler;

import com.cloud.arch.web.error.ErrorHandler;
import org.springframework.http.HttpStatus;

public enum ResponseHandler implements ErrorHandler {
    DATA_STATE_ILLEGAL(HttpStatus.BAD_REQUEST, 400, "操作状态非法"),
    DATA_SAVE_ERROR(HttpStatus.SERVICE_UNAVAILABLE, 500, "数据保存错误或重复操作"),
    PAY_CHANNEL_UNAVAILABEL(HttpStatus.BAD_REQUEST, 400, "支付渠道不可用"),
    PAY_CHANNEL_NONE(HttpStatus.BAD_REQUEST, 400, "支付渠道不存在"),
    PAY_CHANNEL_EXIST(HttpStatus.BAD_REQUEST, 400, "支付渠道已存在"),
    TRANSFER_SCENE_NONE(HttpStatus.BAD_REQUEST, 400, "转账场景不存在"),
    TRANSFER_SCENE_EXIST(HttpStatus.BAD_REQUEST, 400, "转账场景已存在"),
    RULE_MAX_ERROR(HttpStatus.BAD_REQUEST, 400, "提现规则上限错误"),
    RULE_PRE_START_TIME_NONE(HttpStatus.BAD_REQUEST, 400, "预发布规则启用时间不允许为空"),
    TRANSFER_RULE_NONE(HttpStatus.BAD_REQUEST, 400, "提现规则不存在"),
    TRANSFER_ACCOUNT_NONE(404, "提现账户不存在"),
    TRANSFER_NOT_SATISFIED(403, "提现不满足规则"),
    TRANSFER_RECORD_NONE(404, "提现记录不存在"),
    BALANCE_NOT_ENOUGH(403, "账户余额不足"),
    ALI_PAY_NOT_AUTHED(403, "支付宝未授权"),
    WX_PAY_NOT_AUTHED(403, "微信未授权"),
    CHARGE_CONF_NONE(HttpStatus.BAD_REQUEST, 400, "充值配置不存在"),
    CHARGE_CONF_UNAVAILABLE(HttpStatus.BAD_REQUEST, 400, "充值配置不可用"),
    PACK_UN_AVAILABLE(HttpStatus.BAD_REQUEST, 400, "套餐不可用"),
    PACK_NONE(HttpStatus.BAD_REQUEST, 400, "套餐不存在"),
    PACK_NAME_EXIST(HttpStatus.BAD_REQUEST, 400, "套餐名称已存在"),
    PACK_PRICE_ERROR(HttpStatus.BAD_REQUEST, 400, "套餐价格错误"),
    PACK_DELETE_ERROR(HttpStatus.FORBIDDEN, 403, "无权删除套餐"),
    TIME_UNIT_ERROR(HttpStatus.BAD_REQUEST, 400, "时间单位错误"),
    PACK_PRIVILEGE_NULL(HttpStatus.BAD_REQUEST, 400, "套餐特权为空"),
    PACK_PRIVILEGE_EXIST(HttpStatus.BAD_REQUEST, 400, "套餐特权已存在"),
    PRIVILEGE_SORT_ERROR(HttpStatus.BAD_REQUEST, 400, "套餐排序错误"),
    ORDER_NOT_EXIST(HttpStatus.BAD_REQUEST, 400, "订单不存在"),
    ORDER_STATE_ERROR(HttpStatus.FORBIDDEN, 403, "订单状态错误"),
    ORDER_HAS_EXPIRED(HttpStatus.FORBIDDEN, 403, "订单已超时过期"),
    ORDER_QUERY_FORBIDDEN(HttpStatus.FORBIDDEN, 403, "无权查看订单"),
    AUDIT_STATE_ERROR(HttpStatus.BAD_REQUEST, 400, "审核状态错误"),
    AUDIT_THROTTLE_NONE_NULL(HttpStatus.BAD_REQUEST, 400, "审核门槛不允许为空");

    private final HttpStatus status;
    private final Integer    code;
    private final String     message;

    ResponseHandler(Integer code, String message) {
        this(HttpStatus.OK, code, message);
    }

    ResponseHandler(HttpStatus status, Integer code, String message) {
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
