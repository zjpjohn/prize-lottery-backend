package com.prize.lottery.domain.transfer.model.aggregate;

import com.cloud.arch.aggregate.AggregateRoot;
import com.prize.lottery.infrast.persist.enums.AuditState;
import com.prize.lottery.infrast.persist.enums.AuditStep;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransferAuditDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 3532755818596615995L;

    private Long       id;
    private String     auditNo;
    private String     transNo;
    private AuditState state;
    private AuditStep  step;
    private String     reason;
    private Long       auditId;
    private String     auditor;
    private String     lastNo;
    private Long       nextAudit;

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}
