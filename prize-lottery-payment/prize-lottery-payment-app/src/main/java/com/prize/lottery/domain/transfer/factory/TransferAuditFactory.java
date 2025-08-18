package com.prize.lottery.domain.transfer.factory;

import com.cloud.arch.utils.IdWorker;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.domain.transfer.model.aggregate.TransferAuditDo;
import com.prize.lottery.infrast.persist.enums.AuditState;
import com.prize.lottery.infrast.persist.enums.AuditStep;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TransferAuditFactory {

    public TransferAuditDo initialize(String transNo) {
        TransferAuditDo audit = new TransferAuditDo();
        audit.setTransNo(transNo);
        audit.setAuditNo(String.valueOf(IdWorker.nextId()));
        audit.setReason("提现订单开始审核");
        audit.setState(AuditState.PROCESSING);
        audit.setStep(AuditStep.START);
        audit.setAuditId(0L);
        audit.setAuditor("审核助手");
        return audit;
    }

    public TransferAuditDo auditStep(String transNo) {
        return null;
    }

    /**
     * 结束审核
     *
     * @param transNo 提现编号
     * @param state   是否通过审核
     * @param reason  审核不通过原因
     */
    public TransferAuditDo endAudit(String transNo, AuditState state, String reason) {
        Assert.state(state == AuditState.ADOPTED || state == AuditState.REJECTED);
        TransferAuditDo audit = new TransferAuditDo();
        audit.setTransNo(transNo);
        audit.setAuditNo(String.valueOf(IdWorker.nextId()));
        audit.setReason(reason);
        audit.setState(state);
        audit.setStep(AuditStep.END);
        audit.setAuditId(0L);
        audit.setAuditor("审核助手");
        audit.setReason(reason);
        return audit;
    }

}
