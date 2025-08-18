package com.prize.lottery.application.query.vo;

import com.prize.lottery.domain.facade.dto.UserInfo;
import com.prize.lottery.infrast.persist.enums.AuditState;
import com.prize.lottery.pay.PayChannel;
import com.prize.lottery.transfer.TransferState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdmTransferRecordVo {

    private UserInfo      user;
    private String        transNo;
    private String        bizNo;
    private Long          amount;
    private PayChannel    channel;
    private TransferState state;
    private AuditState    audit;
    private String        remark;
    private String        failReason;
    private LocalDateTime latestTime;
    private LocalDateTime gmtCreate;

}
