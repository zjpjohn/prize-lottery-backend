package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.AuditState;
import com.prize.lottery.pay.PayChannel;
import com.prize.lottery.transfer.TransferState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransferRecordPo {

    private Long          id;
    private String        bizNo;
    private Long          userId;
    private String        openId;
    private String        scene;
    private String        batchNo;
    private String        transNo;
    private Long          amount;
    private PayChannel    channel;
    private TransferState state;
    private AuditState    audit;
    private String        remark;
    private String        failReason;
    private LocalDateTime latestTime;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
