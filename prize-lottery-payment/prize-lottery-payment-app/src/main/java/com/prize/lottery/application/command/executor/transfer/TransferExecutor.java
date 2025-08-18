package com.prize.lottery.application.command.executor.transfer;


import com.cloud.arch.executor.Executor;
import com.prize.lottery.application.consumer.event.PayTransferEvent;
import com.prize.lottery.infrast.persist.enums.AuditState;
import com.prize.lottery.pay.PayChannel;

public interface TransferExecutor extends Executor<PayChannel> {

    /**
     * 发起转账
     */
    void payTransfer(PayTransferEvent event);

    /**
     * 审核转账
     */
    void auditTransfer(String transNo, AuditState state, String reason);
}
