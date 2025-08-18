package com.prize.lottery.application.consumer.event;

import com.cloud.arch.event.annotations.Subscribe;
import com.prize.lottery.application.command.event.TransCallbackEvent;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Subscribe(name = "cloud-lottery-user-topic", filter = "user-withdraw", key = "bizNo")
public class PayTransferEvent implements Serializable {

    private static final long serialVersionUID = -5537606545544813941L;

    /**
     * 用户标识
     */
    private Long   userId;
    /**
     * 支付账户标识
     */
    private String openId;
    /**
     * 业务单号
     */
    private String bizNo;
    /**
     * 支付场景标识
     */
    private String scene;
    /**
     * 支付渠道
     */
    private String channel;
    /**
     * 提现金额
     */
    private Long   amount;

    public TransCallbackEvent directCloseEvent(String message) {
        TransCallbackEvent event = new TransCallbackEvent();
        event.setUserId(this.userId);
        event.setScene(this.scene);
        event.setBizNo(this.bizNo);
        event.setChannel(this.channel);
        event.setState(0);
        event.setMessage(message);
        event.setTimestamp(LocalDateTime.now());
        return event;
    }
}
