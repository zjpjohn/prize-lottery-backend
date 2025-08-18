package com.prize.lottery.application.query.vo;

import com.prize.lottery.infrast.persist.enums.PayChannel;
import com.prize.lottery.infrast.persist.enums.WithdrawState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppWithdrawVo {

    private String        bizNo;
    private String        transNo;
    private PayChannel    channel;
    private WithdrawState state;
    private String        message;
    private Long          money;
    private Long          withdraw;
    private LocalDateTime gmtCreate;

}
