package com.prize.lottery.application.consumer.event;

import com.cloud.arch.event.annotations.Subscribe;
import lombok.Data;

@Data
@Subscribe(name = "cloud-lottery-user-topic", filter = "user-charge", key = "orderNo")
public class UserChargeEvent {

    //订单编号
    private String orderNo;
    //用户标识
    private Long   userId;
    //充值金额
    private Long   amount;
    //赠送金币
    private Long   gift;
    //支付方式
    private String channel;
}
