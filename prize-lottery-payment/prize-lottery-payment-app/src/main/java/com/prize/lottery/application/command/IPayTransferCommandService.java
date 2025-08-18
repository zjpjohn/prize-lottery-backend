package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.TransferAuditCmd;
import com.prize.lottery.application.consumer.event.PayTransferEvent;

public interface IPayTransferCommandService {

    void payTransfer(PayTransferEvent event);

    void auditTransfer(TransferAuditCmd command);

}
