package com.prize.lottery.application.consumer.event;

import com.cloud.arch.event.annotations.Subscribe;
import lombok.Data;

@Data
@Subscribe(name = "cloud-lottery-user-topic", filter = "user-member", key = "orderNo")
public class UserMemberEvent {

    //订单编号
    private String  orderNo;
    //用户标识
    private Long    userId;
    //套餐标识
    private String  packNo;
    //套餐名称
    private String  name;
    //套餐市场单位
    private Integer timeUnit;
    //实际支付金额
    private Long    payed;
    //支付方式
    private String  channel;

}
