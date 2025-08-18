package com.prize.lottery.domain.order.event;

import com.cloud.arch.event.annotations.Publish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Publish(name = "cloud-lottery-user-topic", filter = "user-member")
public class OrderMemberEvent {

    //订单编号
    private String  orderNo;
    //用户标识
    private Long    userId;
    //套餐编号
    private String  packNo;
    //套餐名称
    private String  name;
    //套餐时长单位
    private Integer timeUnit;
    //实际支付金额
    private Long    payed;
    //支付渠道
    private String  channel;

}
