package com.prize.lottery.pay;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class UnionPayRequest {

    private PayChannel    channel;
    private String        orderNo;
    private String        subject;
    private String        description;
    private Long          amount;
    private LocalDateTime expireTime;

}
