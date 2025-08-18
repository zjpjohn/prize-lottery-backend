package com.prize.lottery.application.query.vo;

import com.prize.lottery.infrast.persist.enums.AuditState;
import com.prize.lottery.pay.PayChannel;
import com.prize.lottery.transfer.TransferState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppTransferRecordVo {

    private String        transNo;
    private String        scene;
    private Long          amount;
    private PayChannel    channel;
    private TransferState state;
    private AuditState    audit;
    private String        remark;
    private LocalDateTime latestTime;
    private LocalDateTime gmtCreate;

}
