package com.prize.lottery.application.command.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DrawVoucherVo {

    private String        seqNo;
    private Integer       voucher;
    private Integer       disposable;
    private LocalDateTime expireAt;
    private LocalDateTime nextTime;

}
