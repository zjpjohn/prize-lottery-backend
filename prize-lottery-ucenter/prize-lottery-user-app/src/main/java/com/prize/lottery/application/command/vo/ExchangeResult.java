package com.prize.lottery.application.command.vo;

import lombok.Data;

@Data
public class ExchangeResult {

    //消费积分数量
    private Integer coupon;
    //兑换获取金币数量
    private Integer surplus;
    //积分剩余
    private Integer remain;
}
