package com.prize.lottery.infrast.error;

import com.cloud.arch.web.error.ErrorHandler;
import org.springframework.http.HttpStatus;

public enum ResponseErrorHandler implements ErrorHandler {
    STATE_ILLEGAL_ERROR(HttpStatus.BAD_REQUEST, "状态码错误."),
    NOTIFY_TAG_HAS_BIND(HttpStatus.FORBIDDEN, "已有设备绑定，不允许取消"),
    NOTIFY_TAG_NONE(HttpStatus.BAD_REQUEST, "没有标签，请先创建标签"),
    NO_SATISFIED_TAG(HttpStatus.BAD_REQUEST, "没有满足条件的标签"),
    INVALID_APP_NO(HttpStatus.BAD_REQUEST, "无效应用标识"),
    NOTIFY_APP_NONE(HttpStatus.BAD_REQUEST, "应用不存在"),
    NOTIFY_DEVICE_NONE(HttpStatus.BAD_REQUEST, "通知设备不存在"),
    NOTIFY_INFO_NONE(HttpStatus.BAD_REQUEST, "推送信息不存在"),
    OPEN_ACTIVITY_NONE(HttpStatus.BAD_REQUEST, "跳转Activity为空"),
    OPEN_URL_NONE(HttpStatus.BAD_REQUEST, "跳转Url为空"),
    TAG_GROUP_NONE(404, "标签组不存在."),
    INVALID_TAG_GROUP(HttpStatus.BAD_REQUEST, "无效标签分组"),
    DATA_SAVE_ERROR(500, "数据保存失败."),
    CHANNEL_NOT_EXIST(HttpStatus.BAD_REQUEST, 400, "消息渠道不存在"),
    USER_NOT_EXIST(HttpStatus.BAD_REQUEST, 400, "用户不存在"),
    CHANNEL_NO_PRIVILEGE(HttpStatus.FORBIDDEN, 403, "无权访问消息渠道"),
    ANNOUNCE_NOT_MODIFIED(HttpStatus.FORBIDDEN, 403, "公告不允许修改"),
    ANNOUNCE_NOT_EXIST(HttpStatus.BAD_REQUEST, 404, "公告不存在"),
    MESSAGE_LINK_NULL(HttpStatus.BAD_REQUEST, 400, "消息链接为空"),
    MESSAGE_TYPE_NULL(HttpStatus.BAD_REQUEST, 400, "消息类型为空"),
    ;

    private final HttpStatus status;
    private final Integer    code;
    private final String     message;

    ResponseErrorHandler(Integer code, String message) {
        this(HttpStatus.OK, code, message);
    }

    ResponseErrorHandler(HttpStatus status, String message) {
        this(status, status.value(), message);
    }

    ResponseErrorHandler(HttpStatus status, Integer code, String message) {
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
