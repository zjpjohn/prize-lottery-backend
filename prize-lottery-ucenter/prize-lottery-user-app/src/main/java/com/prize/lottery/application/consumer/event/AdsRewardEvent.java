package com.prize.lottery.application.consumer.event;

import com.cloud.arch.event.annotations.Subscribe;
import lombok.Data;

import java.io.Serializable;

@Data
@Subscribe(name = "cloud-lottery-user-topic", filter = "advert-reward", key = "transId")
public class AdsRewardEvent implements Serializable {

    private static final long serialVersionUID = 8867200258601654722L;

    /**
     * 交易流水
     */
    private String  transId;
    /**
     * 奖励事件类型:0-金币奖励,1-抽奖机会奖励
     */
    private Integer type;
    /**
     * 用户标识
     */
    private Long    userId;
    /**
     * 奖励金额
     */
    private Integer reward;
    /**
     * 非提现奖励金额
     */
    private Integer bounty;
    /**
     * 激励视频广告渠道
     */
    private Integer channel;
}
