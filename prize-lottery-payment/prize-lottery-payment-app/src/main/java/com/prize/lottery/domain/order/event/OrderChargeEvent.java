package com.prize.lottery.domain.order.event;

import com.cloud.arch.event.annotations.Publish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Publish(name = "cloud-lottery-user-topic", filter = "user-charge")
public class OrderChargeEvent {

    //订单编号
    private String orderNo;
    //用户标识
    private Long   userId;
    //充值金币数量
    private Long   amount;
    //赠送金币数量
    private Long   gift;
    //支付金额
    private Long   payed;
    //支付渠道
    private String channel;
}
