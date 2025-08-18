package com.prize.lottery.pay;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotifyResult {

    private String        appId;
    private PayChannel    channel;
    private String        orderNo;
    private TradeState    state;
    private Long          total;
    private Long          payTotal;
    private LocalDateTime payTime;
    private LocalDateTime closeTime;

}
