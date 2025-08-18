package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.AuditState;
import com.prize.lottery.pay.PayChannel;
import com.prize.lottery.transfer.TransBatchState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransferBatchPo {

    private Long            id;
    private String          bizNo;
    private String          batchNo;
    private String          batchName;
    private String          remark;
    private String          scene;
    private Long            total;
    private Integer         totalNum;
    private PayChannel      channel;
    private TransBatchState state;
    private AuditState      audit;
    private String          closeReason;
    private Long            operId;
    private Integer         operType;
    private Long            successAmount;
    private Integer         successNum;
    private Long            failAmount;
    private Integer         failNum;
    private LocalDateTime   latestTime;
    private LocalDateTime   gmtCreate;
    private LocalDateTime   gmtModify;


}
