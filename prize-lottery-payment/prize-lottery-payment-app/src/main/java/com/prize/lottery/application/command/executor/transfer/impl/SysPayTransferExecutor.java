package com.prize.lottery.application.command.executor.transfer.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.event.core.publish.GenericEvent;
import com.cloud.arch.event.publisher.DomainEventPublisher;
import com.prize.lottery.application.command.event.TransCallbackEvent;
import com.prize.lottery.application.command.executor.transfer.TransferExecutor;
import com.prize.lottery.application.consumer.event.PayTransferEvent;
import com.prize.lottery.domain.transfer.factory.PayTransferFactory;
import com.prize.lottery.domain.transfer.factory.TransferAuditFactory;
import com.prize.lottery.domain.transfer.model.aggregate.PayTransferOneDo;
import com.prize.lottery.domain.transfer.model.aggregate.TransferAuditDo;
import com.prize.lottery.domain.transfer.repository.IPayTransferRepository;
import com.prize.lottery.domain.transfer.repository.ITransferAuditRepository;
import com.prize.lottery.infrast.persist.enums.AuditState;
import com.prize.lottery.pay.PayChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SysPayTransferExecutor implements TransferExecutor {

    private final PayTransferFactory     payTransferFactory;
    private final TransferAuditFactory     transferAuditFactory;
    private final IPayTransferRepository   payTransferRepository;
    private final ITransferAuditRepository transferAuditRepository;

    @Override
    public PayChannel bizIndex() {
        return PayChannel.SYS_PAY;
    }

    @Override
    public void payTransfer(PayTransferEvent event) {
        //创建提现订单信息
        PayTransferOneDo sysPayTransfer = payTransferFactory.createSysPay(event);
        AggregateFactory.create(sysPayTransfer).save(payTransferRepository::saveOne);

        //后台转账，直接进入审核流程
        TransferAuditDo transferAudit = transferAuditFactory.initialize(sysPayTransfer.getTransNo());
        transferAuditRepository.save(transferAudit);
        //发送提现审核回调事件
        TransCallbackEvent auditEvent   = sysPayTransfer.auditEvent();
        GenericEvent       genericEvent = GenericEvent.create(auditEvent, "cloud-lottery-user-topic", auditEvent.getScene());
        DomainEventPublisher.publish(genericEvent);
    }

    @Override
    public void auditTransfer(String transNo, AuditState state, String reason) {
        Aggregate<Long, PayTransferOneDo> aggregate = payTransferRepository.ofOne(transNo);
        PayTransferOneDo                  transfer  = aggregate.getRoot();
        transfer.audit(state, reason);
        payTransferRepository.saveOne(aggregate);
        if (state == AuditState.ADOPTED || state == AuditState.REJECTED) {
            //发送审核结果回调事件
            TransCallbackEvent event        = transfer.callbackEvent();
            GenericEvent       genericEvent = GenericEvent.create(event, "cloud-lottery-user-topic", event.getScene());
            DomainEventPublisher.publish(genericEvent);
        }
    }

}
