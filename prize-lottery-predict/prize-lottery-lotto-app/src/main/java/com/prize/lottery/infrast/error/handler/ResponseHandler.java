package com.prize.lottery.infrast.error.handler;

import com.cloud.arch.web.error.ErrorHandler;
import org.springframework.http.HttpStatus;

public enum ResponseHandler implements ErrorHandler {

    FAST_TABLE_ERROR(10400, "开奖数据不足"),
    FAST_TABLE_CLOSED(10404, "该彩种未开通速查表"),
    LOTTERY_INFO_ERROR(10404, "暂无开奖数据"),
    MASTER_NONE(10404, "专家不存在"),
    USER_INFO_NONE(10404, "注册用户不存在"),
    FORECAST_NONE(10404, "没有预测数据"),
    PIVOT_NONE(10404, "暂无今日要点信息"),
    AROUND_NONE(10404, "暂无绕胆图信息"),
    HONEY_NONE(10404, "暂无配胆图信息"),
    WARNING_NONE(10404, "暂无预警分析"),
    LAYER_NONE(10404, "暂无预警分析"),
    RECOMMEND_NONE(10404, "没有预测推荐"),
    FORECAST_PRIVILEGE(10403, "账户余额不足."),
    CHART_NOT_MODIFIED(10404, "暂未更新"),
    CHART_TYPE_ERROR(10400, "类型错误"),
    LOTTERY_TYPE_ERROR(10400, "彩种类型错误"),
    AVATAR_NOT_EXISTED(10404, "头像不存在"),
    PASSWORD_NONE_CONSTANT(HttpStatus.BAD_REQUEST, 400, "密码不一致"),
    VERIFY_CODE_ERROR(HttpStatus.BAD_REQUEST, 400, "验证码错误"),
    PHONE_HAS_REGISTER(HttpStatus.BAD_REQUEST, 400, "手机号已注册"),
    MASTER_HAS_REGISTER(HttpStatus.BAD_REQUEST, 400, "账户已注册专家."),
    MASTER_HAS_ENABLE(HttpStatus.BAD_REQUEST, 400, "已开启预测频道"),
    SUBSCRIBE_PARAM_ERROR(HttpStatus.BAD_REQUEST, 400, "订阅专家参数错误."),
    HAS_SUBSCRIBE_MASTER(HttpStatus.BAD_REQUEST, 400, "专家已被订阅."),
    HAS_FOCUS_MASTER(HttpStatus.BAD_REQUEST, 400, "专家已被关注."),
    SUBSCRIBE_MASTER_NONE(HttpStatus.BAD_REQUEST, 400, "订阅记录不存在."),
    TRACE_MASTER_ERROR(HttpStatus.BAD_REQUEST, 400, "追踪专家字段错误."),
    HAS_TRACED_MASTER(HttpStatus.BAD_REQUEST, 400, "已追踪专家字段."),
    FOCUS_MASTER_NONE(HttpStatus.BAD_REQUEST, 400, "关注记录不存在."),
    DEDUCT_EVENT_ERROR(403, "创建扣减事件错误."),
    LOTTERY_NEWS_NONE(404, "资讯不存在"),
    LOTTERY_SKILL_NONE(404, "技巧不存在"),
    LOTTERY_NEWS_FORBIDDEN(403, "资讯不可访问"),
    LOTTERY_SKILL_FORBIDDEN(403, "技巧不可访问"),
    MASTER_BATTLE_HAS_REMOVED(HttpStatus.BAD_REQUEST, 400, "PK对战记录已移除"),
    MASTER_BATTLE_NONE(HttpStatus.BAD_REQUEST, 400, "PK对战记录不存在"),
    MASTER_BATTLE_PERIOD_ERROR(HttpStatus.BAD_REQUEST, 400, "PK对战期号错误"),
    INTELLECT_PERIOD_ERROR(HttpStatus.BAD_REQUEST, 400, "暂无指数信息"),
    SUM_RANGE_ERROR(HttpStatus.BAD_REQUEST, 400, "和值范围错误"),
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
