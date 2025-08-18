package com.prize.lottery.infrast.error.handle;

import com.cloud.arch.web.error.ErrorHandler;
import org.springframework.http.HttpStatus;

public enum ResponseHandler implements ErrorHandler {
    NONE_FETCHED_RESULT(404, "没有抓取到数据."),
    NO_OPEN_LOTTERY(HttpStatus.BAD_REQUEST, "没有开奖数据"),
    NO_CALCULATE_DATA(HttpStatus.BAD_REQUEST, "没有未计算数据"),
    HAS_EXIST_HISTORY(HttpStatus.BAD_REQUEST, "历史数据已存在"),
    HAS_INIT_DATA(HttpStatus.BAD_REQUEST, "已初始化数据"),
    NO_NAME_DATABASE(HttpStatus.BAD_REQUEST, "名字数据资源不存在"),
    NO_MASTER_DATABASE(HttpStatus.BAD_REQUEST, "专家数据资源不存在"),
    IMPORT_NAME_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "导入专家名称错误"),
    RESET_NAME_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "重置专家名称错误"),
    NO_AVATAR_LIST(HttpStatus.BAD_REQUEST, "头像集合不存在"),
    RESET_AVATAR_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "重置头像异常"),
    HAS_INIT_OMIT(HttpStatus.BAD_REQUEST, "已初始化遗漏数据"),
    NO_INIT_OMIT(HttpStatus.INTERNAL_SERVER_ERROR, "没有初始化遗漏数据"),
    LOTTO_TYPE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "彩票类型错误"),
    NO_FORECAST_DATA(404, "没有预测数据"),
    NO_LOTTERY_OMIT(400, "没有对应期的遗漏数据"),
    OMIT_NOT_CONSISTENT(400, "开奖数据与遗漏数据不连续"),
    HAS_CALCULATED_DATA(400, "数据已计算"),
    NO_RANKED_DATA(404, "没有排名信息"),
    HAS_RANKED_DATA(400, "专家已排名计算"),
    NO_LOTTERY_MASTER(400, "没有预测专家"),
    NO_FETCH_HISTORY(400, "没有可抓取历史数据"),
    HAS_EXIST_MASTER(400, "专家已存在"),
    COM_WARN_ERROR(400, "预警结果错误"),
    LAYER_FILTER_EMPTY(404, "没有预警数据"),
    ;

    private final HttpStatus status;
    private final Integer    code;
    private final String     message;

    ResponseHandler(HttpStatus status, String message) {
        this.status  = status;
        this.message = message;
        this.code    = status.value();
    }

    ResponseHandler(HttpStatus status, Integer code, String message) {
        this.status  = status;
        this.code    = code;
        this.message = message;
    }

    ResponseHandler(Integer code, String message) {
        this(HttpStatus.OK, code, message);
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
