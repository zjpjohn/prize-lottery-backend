package com.prize.lottery.infrast.error.handler;

import com.cloud.arch.web.error.ErrorHandler;
import org.springframework.http.HttpStatus;

public enum ResponseHandler implements ErrorHandler {
    DATA_SAVE_ERROR(HttpStatus.BAD_REQUEST, 400, "数据保存错误"),
    ENUM_VALUE_UNKNOWN(HttpStatus.BAD_REQUEST, 400, "枚举值未知"),
    DATA_STATE_ILLEGAL(HttpStatus.BAD_REQUEST, 400, "操作状态非法"),
    PASSWORD_NONE_CONSTANT(HttpStatus.BAD_REQUEST, 400, "密码不一致"),
    VERIFY_CODE_ERROR(HttpStatus.BAD_REQUEST, 400, "验证码错误"),
    MOBILE_INVALID_ERROR(HttpStatus.BAD_REQUEST, 400, "无效手机号"),
    MOBILE_HAS_EXISTED(HttpStatus.BAD_REQUEST, 400, "手机号已经存在"),
    ACCOUNT_NONE_EXISTED(HttpStatus.BAD_REQUEST, 400, "登录账户不存在"),
    ACCOUNT_EXCEPTION(HttpStatus.BAD_REQUEST, 400, "登录账户异常"),
    ILLEGAL_APP_DEVICE(HttpStatus.FORBIDDEN, 403, "非法登录设备"),
    PASSWORD_ERROR(HttpStatus.BAD_REQUEST, 400, "登录密码错误"),
    SAVE_USER_ERROR(HttpStatus.BAD_REQUEST, 400, "创建用户错误"),
    USER_NONE_PRIVILEGE(HttpStatus.FORBIDDEN, 403, "无权操作"),
    REGISTER_CHANNEL_NONE(HttpStatus.BAD_REQUEST, 404, "注册渠道错误"),
    USER_INVITE_NONE(404, "邀请账户不存在"),
    USER_INVITE_LOG_NONE(404, "邀请记录不存在"),
    INVITE_CODE_ILLEGAL(HttpStatus.BAD_REQUEST, 400, "邀请码错误"),
    USER_MASTER_NONE(404, "用户专家不存在"),
    USER_INFO_NONE(404, "用户不存在"),
    USER_BALANCE_NONE(404, "余额账户不存在"),
    USER_EXPERT_NONE(404, "专家账户不存在"),
    MANUAL_CHARGE_FORBIDDEN(HttpStatus.FORBIDDEN, 403, "账户不允许手动充值"),
    WITHDRAW_RULE_NONE(404, "提现规则不存在"),
    WITHDRAW_RULE_CREATED_CAN_EDIT(403, "仅创建规则不允许修改"),
    WITHDRAW_ILLEGAL(403, "不满足提现规则，请稍后再试"),
    WITHDRAW_STATE_ILLEGAL(400, "提现状态错误"),
    WITHDRAW_RECORD_NONE(HttpStatus.BAD_REQUEST, 400, "提现记录不存在"),
    USER_HAS_SIGNED(401, "今日已签到"),
    PASSWORD_HAS_SAME(HttpStatus.BAD_REQUEST, 400, "新旧密码相同"),
    FEEDBACK_NONE(HttpStatus.BAD_REQUEST, 400, "用户反馈不存在"),
    FEEDBACK_HAS_HANDLED(204, "用户反馈已处理"),
    COUPON_EXCHANGE_ILLEGAL(HttpStatus.FORBIDDEN, 403, "积分余额不足"),
    BALANCE_DEDUCT_ILLEGAL(403, "账户金币不足"),
    ADVERTISE_ILLEGAL(400, "广告无效"),
    ADVERTISE_TIME_ILLEGAL(400, "广告时间无效"),
    ADVERTISE_NONE_NULL(400, "广告不存在"),
    ADVERTISE_PRIVILEGE(HttpStatus.FORBIDDEN, 403, "无权操作广告"),
    REWARD_RULE_NOT_NULL(HttpStatus.BAD_REQUEST, 400, "金币奖励规则不允许为空"),
    REWARD_VALUE_ERROR(HttpStatus.BAD_REQUEST, 400, "金币奖励金额应大于0"),
    RATIO_RULE_NOT_NULL(HttpStatus.BAD_REQUEST, 400, "分润比例规则不允许为空"),
    AGENT_RULE_NOT_NULL(404, "代理规则不存在"),
    AGENT_RULE_STATE_ERROR(403, "代理规则状态错误"),
    AGENT_RULE_PRE_START_ERROR(403, "禁止预发布规则"),
    AGENT_RULE_USING_ERROR(403, "禁止发布规则"),
    RULE_HINT_NOT_NULL(400, "规则变更提醒不存在"),
    RULE_HINT_MODIFY_ERROR(403, "规则变更提醒禁止修改"),
    AGENT_APPLY_STATE_ERROR(403, "申请状态禁止操作"),
    AGENT_APPLY_NOT_NULL(400, "代理申请不存在"),
    AGENT_APPLY_HAS_EXIST(400, "已有申请待审核"),
    AGENT_APPLY_FORBIDDEN(HttpStatus.FORBIDDEN, 403, "无权操作代理申请"),
    AGENT_LEVEL_NOT_NULL(HttpStatus.BAD_REQUEST, 400, "代理等级为空"),
    CONFIRM_REMARK_NOT_NULL(HttpStatus.BAD_REQUEST, 400, "审核说明为空"),
    RULE_MAX_ERROR(HttpStatus.BAD_REQUEST, 400, "提现规则上限错误"),
    RULE_PRE_START_TIME_NONE(HttpStatus.BAD_REQUEST, 400, "预发布规则启用时间不允许为空"),
    WITHDRAW_FORBIDDEN(HttpStatus.FORBIDDEN, 403, "无权进行提现"),
    WITHDRAW_CHANNEL_NONE(HttpStatus.FORBIDDEN, 11403, "请设置支付渠道"),
    VOUCHER_INTERVAL_ERROR(HttpStatus.BAD_REQUEST, 400, "代金券间隔时间错误"),
    VOUCHER_NOT_EXIST(HttpStatus.BAD_REQUEST, 400, "代金券不存在"),
    VOUCHER_CAN_NOT_DRAW(HttpStatus.FORBIDDEN, 403, "无权领取代金券"),
    WITHDRAW_LEVEL_NONE(HttpStatus.BAD_REQUEST, 404, "提现等级不存在"),
    MOBILE_SMS_CHANNEL_ERROR(401, "手机短信渠道错误"),
    PUT_CHANNEL_NONE(HttpStatus.BAD_REQUEST, 400, "投放渠道不存在"),
    PUT_RECORD_NONE(HttpStatus.BAD_REQUEST, 400, "投放记录不存在"),
    RECORD_STATE_ERROR(HttpStatus.BAD_REQUEST, 400, "投放状态错误"),
    ACTIVITY_MEMBER_NONE(HttpStatus.BAD_REQUEST, 400, "暂无抽奖活动"),
    ACTIVITY_DRAW_FORBIDDEN(HttpStatus.FORBIDDEN, 403, "无权访问抽奖活动"),
    ACTIVITY_HAS_DRAWN(400, "今日已抽奖"),
    CHANCES_NOT_ENOUGH(400, "抽奖机会不足"),
    ACTIVITY_USER_NONE(404, "暂无抽奖用户信息"),
    ACTIVITY_DRAW_NONE(404, "抽奖记录不存在"),
    USER_MEMBER_NONE(400, "暂未开通会员"),
    DRAW_STATE_ERROR(400, "抽奖状态非法"),
    DRAW_RESULT_NONE(400, "抽奖信息不存在"),
    PACK_UN_AVAILABLE(HttpStatus.BAD_REQUEST, 400, "套餐不可用"),
    PACK_NONE(HttpStatus.BAD_REQUEST, 400, "套餐不存在"),
    PACK_NAME_EXIST(HttpStatus.BAD_REQUEST, 400, "套餐名称已存在"),
    PACK_PRICE_ERROR(HttpStatus.BAD_REQUEST, 400, "套餐价格错误"),
    PACK_DELETE_ERROR(HttpStatus.FORBIDDEN, 403, "无权删除套餐"),
    TIME_UNIT_ERROR(HttpStatus.BAD_REQUEST, 400, "时间单位错误"),
    PACK_PRIVILEGE_NULL(HttpStatus.BAD_REQUEST, 400, "套餐特权为空"),
    PACK_PRIVILEGE_EXIST(HttpStatus.BAD_REQUEST, 400, "套餐特权已存在"),
    PRIVILEGE_SORT_ERROR(HttpStatus.BAD_REQUEST, 400, "套餐排序错误"),
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
