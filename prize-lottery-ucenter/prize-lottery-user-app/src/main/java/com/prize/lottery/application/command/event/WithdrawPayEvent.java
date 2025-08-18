package com.prize.lottery.application.command.event;

import com.cloud.arch.event.annotations.Publish;
import lombok.Data;

import java.io.Serializable;

@Data
@Publish(
        name = "cloud-lottery-user-topic", filter = "user-withdraw")
public class WithdrawPayEvent implements Serializable {

    private static final long serialVersionUID = -1838287506094880724L;

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

}
