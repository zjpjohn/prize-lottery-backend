package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.AuditState;
import com.prize.lottery.infrast.persist.enums.AuditStep;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransferAuditPo {

    private Long          id;
    private String        auditNo;
    private String     transNo;
    private AuditState state;
    private AuditStep  step;
    private String     reason;
    private Long          auditId;
    private String        auditor;
    private String        lastNo;
    private Long          nextAudit;
    private LocalDateTime gmtCreate;

}
