package com.prize.lottery.infrast.error.handler;

import com.cloud.arch.web.error.ErrorHandler;
import org.springframework.http.HttpStatus;

public enum ResponseHandler implements ErrorHandler {
    DATA_SAVED_ERROR(HttpStatus.BAD_REQUEST, 400, "已存在相关数据"),
    DATA_STATE_ILLEGAL(HttpStatus.BAD_REQUEST, 400, "操作状态非法"),
    ADMIN_ACCT_EXIST(HttpStatus.BAD_REQUEST, 400, "账户已存在"),
    ADMIN_ACCT_NONE(HttpStatus.BAD_REQUEST, 400, "账户不存在"),
    ADMIN_NONE_PRIVILEGE(HttpStatus.FORBIDDEN, 403, "账户无权操作"),
    ADMIN_ACCT_INVALID(HttpStatus.UNAUTHORIZED, 401, "账户无效"),
    PASSWORD_NOT_CONSTANT(HttpStatus.BAD_REQUEST, 400, "密码不一致"),
    RESET_PWD_NOT_SAME(HttpStatus.BAD_REQUEST, 400, "新密码不能和原始密码相同"),
    ADMIN_STATE_ILLEGAL(400, "状态非法"),
    AUTH_PASSWORD_ERROR(HttpStatus.BAD_REQUEST, 400, "登录密码错误"),
    APP_NAME_EXIST(HttpStatus.BAD_REQUEST, 400, "应用名已存在"),
    APP_NOT_EXIST(HttpStatus.BAD_REQUEST, 400, "应用不存在"),
    APP_VERSION_EXIST(HttpStatus.BAD_REQUEST, 400, "应用版本已存在"),
    APP_MAIN_VERSION_NONE(404, "应用主推版本不存在"),
    APP_VERSION_NOT_EXIST(HttpStatus.BAD_REQUEST, 400, "应用版本不存在"),
    APP_VERSION_STATE_ILLEGAL(400, "版本状态非法"),
    APP_RESOURCE_NONE(HttpStatus.BAD_REQUEST, 400, "应用资源部存在"),
    APP_RESOURCE_EXIST(HttpStatus.BAD_REQUEST, 400, "资源已存在"),
    NOTICE_NOT_EXIST(400, "须知内容不存在"),
    NOTICE_STATE_ILLEGAL(400, "须知状态错误"),
    OSS_CALLBACK_ERROR(401, "回调检验失败"),
    BANNER_NOT_EXIST(404, "banner不存在"),
    BANNER_NOT_ALLOW_MODIFY(403, "banner不允许修改"),
    ASSISTANT_NOT_EXIST(404, "应用助手不存在"),
    INVALID_APP_VERSION(HttpStatus.BAD_REQUEST, 400, "无效应用版本"),
    SORT_INDEX_ERROR(HttpStatus.BAD_REQUEST, 400, "排序标识参数错误"),
    INVALID_APP_NO(HttpStatus.BAD_REQUEST, 400, "无效应用标识"),
    APP_VERIFY_NONE(HttpStatus.BAD_REQUEST, 404, "授权信息不存在"),
    APP_CONF_NONE(HttpStatus.BAD_REQUEST, 404, "应用配置不存在"),
    APP_COMMENT_NONE(HttpStatus.BAD_REQUEST, 400, "应用评论不存在"),
    COMMENT_LIST_EMPTY(HttpStatus.BAD_REQUEST, 400, "评论列表为空"),
    COMMENT_JSON_MALFORMED(HttpStatus.BAD_REQUEST, 400, "评论列表格式错误"),
    FEEDBACK_HAS_HANDLED(204, "用户反馈已处理"),
    FEEDBACK_NOT_EXIST(404, "应用反馈不存在"),
    CONTACT_NOT_EXIST(404, "联系人不存在"),
    FEEDBACK_TYPE_NOT_EXIST(404, "反馈类型不存在"),
    AD_MEDIA_NONE(HttpStatus.BAD_REQUEST, 404, "广告媒体不存在"),
    AD_UNIT_EXIST(HttpStatus.BAD_REQUEST, 400, "广告位已存在"),
    AD_UNIT_NONE(HttpStatus.BAD_REQUEST, 404, "广告位不存在"),
    ;

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
        return status;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getError() {
        return message;
    }
}
