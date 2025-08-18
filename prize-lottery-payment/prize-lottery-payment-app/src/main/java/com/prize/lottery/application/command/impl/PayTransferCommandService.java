package com.prize.lottery.application.command.impl;

import com.cloud.arch.event.core.publish.GenericEvent;
import com.cloud.arch.event.publisher.DomainEventPublisher;
import com.cloud.arch.executor.EnumerableExecutorFactory;
import com.prize.lottery.application.command.IPayTransferCommandService;
import com.prize.lottery.application.command.dto.TransferAuditCmd;
import com.prize.lottery.application.command.event.TransCallbackEvent;
import com.prize.lottery.application.command.executor.transfer.TransferExecutor;
import com.prize.lottery.application.consumer.event.PayTransferEvent;
import com.prize.lottery.domain.transfer.factory.TransferAuditFactory;
import com.prize.lottery.domain.transfer.model.aggregate.PayChannelDo;
import com.prize.lottery.domain.transfer.model.aggregate.TransferAuditDo;
import com.prize.lottery.domain.transfer.repository.IPayChannelRepository;
import com.prize.lottery.domain.transfer.repository.IPayTransferRepository;
import com.prize.lottery.domain.transfer.repository.ITransferAuditRepository;
import com.prize.lottery.infrast.persist.enums.AuditState;
import com.prize.lottery.pay.PayChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayTransferCommandService implements IPayTransferCommandService {

    private final TransferAuditFactory                                    transferAuditFactory;
    private final IPayTransferRepository                                  payTransferRepository;
    private final ITransferAuditRepository                                transferAuditRepository;
    private final IPayChannelRepository                                   payChannelRepository;
    private final EnumerableExecutorFactory<PayChannel, TransferExecutor> transferExecutorFactory;

    @Override
    @Transactional
    public void payTransfer(PayTransferEvent event) {
        PayChannelDo payChannel = payChannelRepository.ofChannel(event.getChannel());
        if (payChannel == null || !payChannel.canWithdraw()) {
            TransCallbackEvent transEvent   = event.directCloseEvent("无效支付通道");
            GenericEvent       genericEvent = GenericEvent.create(transEvent, "cloud-lottery-user-topic", transEvent.getScene());
            DomainEventPublisher.publish(genericEvent);
            return;
        }
        PayChannel.of(event.getChannel())
                  .flatMap(transferExecutorFactory::ofNullable)
                  .ifPresent(executor -> executor.payTransfer(event));
    }

    @Override
    @Transactional
    public void auditTransfer(TransferAuditCmd command) {
        String transNo = command.getTransNo();
        if (!payTransferRepository.isAuditing(transNo)) {
            return;
        }
        //创建审核记录
        AuditState      auditState    = AuditState.of(command.getAudit());
        TransferAuditDo transferAudit = transferAuditFactory.endAudit(transNo, auditState, command.getMessage());
        transferAuditRepository.save(transferAudit);
        //审核提现订单
        transferExecutorFactory.of(command.getChannel()).auditTransfer(transNo, auditState, command.getMessage());
    }

}
