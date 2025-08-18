package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.enums.PayChannel;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import lombok.Data;

@Data
public class WithdrawDto {

    private Long          userId;
    private PayChannel    channel;
    private TransferScene scene;
    private Long          amount;
    private String        seqNo;

}
