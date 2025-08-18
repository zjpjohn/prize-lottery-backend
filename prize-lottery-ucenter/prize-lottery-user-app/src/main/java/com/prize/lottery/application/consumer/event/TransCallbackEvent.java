package com.prize.lottery.application.consumer.event;

import com.cloud.arch.event.annotations.Subscribe;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Subscribe(name = "cloud-lottery-user-topic", filter = "USER_REWARD_TRANS", key = "bizNo")
@Subscribe(name = "cloud-lottery-user-topic", filter = "USER_AGENT_TRANS", key = "bizNo")
@Subscribe(name = "cloud-lottery-user-topic", filter = "USER_EXPERT_TRANS", key = "bizNo")
public class TransCallbackEvent implements Serializable {

    private static final long serialVersionUID = -7861132171049411156L;

    /**
     * 用户标识
     */
    private Long          userId;
    /**
     * 支付场景标识
     */
    private String        scene;
    /**
     * 支付渠道标识
     */
    private String        channel;
    /**
     * 支付系统流水号
     */
    private String        transNo;
    /**
     * 本系统提现业务编号
     */
    private String        bizNo;
    /**
     * 提现回调状态码: 0-审核未通过(关单), 2-提现审核中, 3-提现失败(关单), 4-提现成功(成功完成)
     */
    private Integer       state;
    /**
     * 提现失败关单原因
     */
    private String        message;
    /**
     * 最新更新时间
     */
    private LocalDateTime timestamp;

    /**
     * 提现是否成功
     */
    public boolean isSuccess() {
        return this.state == 4;
    }

    /**
     * 提现是否失败
     */
    public boolean isFailure() {
        return this.state == 0 || this.state == 3;
    }

}
