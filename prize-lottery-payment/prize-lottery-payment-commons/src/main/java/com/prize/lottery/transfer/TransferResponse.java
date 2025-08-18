package com.prize.lottery.transfer;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransferResponse {

    private String        transNo;
    private String        orderId;
    private String        state;
    private LocalDateTime transTime;

}
