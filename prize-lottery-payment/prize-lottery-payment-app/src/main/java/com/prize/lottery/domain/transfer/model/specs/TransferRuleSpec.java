package com.prize.lottery.domain.transfer.model.specs;

import com.prize.lottery.infrast.persist.enums.AuditState;
import com.prize.lottery.infrast.persist.po.TransferRulePo;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class TransferRuleSpec {

    private final Long    throttle;
    private final Integer force;

    /**
     * 判断当前金额是否需要审核
     *
     * @param amount 提现金额
     */
    public AuditState needAudit(Long amount) {
        if (this.force == 1 || amount > this.throttle) {
            return AuditState.PROCESSING;
        }
        return AuditState.NONE;
    }

    public static TransferRuleSpec from(TransferRulePo rule) {
        return new TransferRuleSpec(rule.getThrottle(), rule.getForce());
    }

}
