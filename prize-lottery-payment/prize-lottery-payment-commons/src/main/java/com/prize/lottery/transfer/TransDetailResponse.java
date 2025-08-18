package com.prize.lottery.transfer;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransDetailResponse {

    private String        transNo;
    private TransferState state;
    private LocalDateTime latestTime;
    private String        failReason;

}
