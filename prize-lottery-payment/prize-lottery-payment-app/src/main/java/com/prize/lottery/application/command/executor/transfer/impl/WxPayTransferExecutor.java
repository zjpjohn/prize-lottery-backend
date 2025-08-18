package com.prize.lottery.application.command.executor.transfer.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.event.core.publish.GenericEvent;
import com.cloud.arch.event.publisher.DomainEventPublisher;
import com.cloud.arch.executor.EnumerableExecutorFactory;
import com.prize.lottery.application.command.event.TransCallbackEvent;
import com.prize.lottery.application.command.executor.transfer.TransCheckExecutor;
import com.prize.lottery.application.command.executor.transfer.TransferExecutor;
import com.prize.lottery.application.consumer.event.PayTransferEvent;
import com.prize.lottery.domain.transfer.ability.PayTransferExecutorAbility;
import com.prize.lottery.domain.transfer.factory.PayTransferFactory;
import com.prize.lottery.domain.transfer.factory.TransferAuditFactory;
import com.prize.lottery.domain.transfer.model.aggregate.PayTransferBatchDo;
import com.prize.lottery.domain.transfer.model.aggregate.TransferAuditDo;
import com.prize.lottery.domain.transfer.model.entity.TransferRecord;
import com.prize.lottery.domain.transfer.repository.IPayTransferRepository;
import com.prize.lottery.domain.transfer.repository.ITransferAuditRepository;
import com.prize.lottery.error.PayRequestException;
import com.prize.lottery.infrast.persist.enums.AuditState;
import com.prize.lottery.pay.PayChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Slf4j
@Component
public class WxPayTransferExecutor implements TransferExecutor {

    private final Executor                                                  executor;
    private final PayTransferFactory                                        payTransferFactory;
    private final TransferAuditFactory                                      transferAuditFactory;
    private final IPayTransferRepository                                    payTransferRepository;
    private final ITransferAuditRepository                                  transferAuditRepository;
    private final PayTransferExecutorAbility                                payTransferExecutorAbility;
    private final EnumerableExecutorFactory<PayChannel, TransCheckExecutor> transCheckExecutorFactory;

    public WxPayTransferExecutor(@Qualifier("asyncPayExecutor") Executor executor,
                                 PayTransferFactory payTransferFactory,
                                 TransferAuditFactory transferAuditFactory,
                                 IPayTransferRepository payTransferRepository,
                                 ITransferAuditRepository transferAuditRepository,
                                 PayTransferExecutorAbility payTransferExecutorAbility,
                                 EnumerableExecutorFactory<PayChannel, TransCheckExecutor> transCheckExecutorFactory) {
        this.executor                   = executor;
        this.payTransferFactory         = payTransferFactory;
        this.transferAuditFactory       = transferAuditFactory;
        this.payTransferRepository      = payTransferRepository;
        this.transferAuditRepository    = transferAuditRepository;
        this.payTransferExecutorAbility = payTransferExecutorAbility;
        this.transCheckExecutorFactory  = transCheckExecutorFactory;
    }

    @Override
    public PayChannel bizIndex() {
        return PayChannel.WX_PAY;
    }

    @Override
    public void payTransfer(PayTransferEvent event) {
        PayTransferBatchDo wxPayTransfer = payTransferFactory.createWxPay(event);
        AggregateFactory.create(wxPayTransfer).save(payTransferRepository::saveBatch);
        //无需审核直接提现
        if (wxPayTransfer.getAudit() == AuditState.NONE) {
            this.asyncWxTransfer(wxPayTransfer);
            return;
        }
        //待审核后，后台发起提现
        TransferRecord  transferRecord = wxPayTransfer.getRecord();
        String          transNo        = transferRecord.getTransNo();
        TransferAuditDo transferAudit  = transferAuditFactory.initialize(transNo);
        transferAuditRepository.save(transferAudit);
        TransCallbackEvent auditEvent   = transferRecord.auditEvent();
        GenericEvent       genericEvent = GenericEvent.create(auditEvent, "cloud-lottery-user-topic", auditEvent.getScene());
        DomainEventPublisher.publish(genericEvent);
    }

    @Override
    public void auditTransfer(String transNo, AuditState state, String reason) {
        Aggregate<Long, PayTransferBatchDo> aggregate     = payTransferRepository.batchOfTransNo(transNo);
        PayTransferBatchDo                  transferBatch = aggregate.getRoot();
        transferBatch.audit(state, reason);
        payTransferRepository.saveBatch(aggregate);
        if (state == AuditState.ADOPTED) {
            //审核通过异步发起支付
            this.asyncWxTransfer(transferBatch);
            return;
        }
        if (state == AuditState.REJECTED) {
            //审核不通过，推送关单回调事件
            TransferRecord     transferRecord = transferBatch.getRecord();
            TransCallbackEvent auditEvent     = transferRecord.callbackEvent();
            GenericEvent       genericEvent   = GenericEvent.create(auditEvent, "cloud-lottery-user-topic", auditEvent.getScene());
            DomainEventPublisher.publish(genericEvent);
        }
    }

    /**
     * 异步调用微信转账接口
     */
    private void asyncWxTransfer(PayTransferBatchDo transfer) {
        executor.execute(() -> {
            try {
                payTransferExecutorAbility.executeWxPay(transfer);
            } catch (PayRequestException error) {
                log.error(error.getError(), error);
                //调用支付接口错误，回退转账
                if (error.isRollback()) {
                    TransferRecord record = transfer.getRecord();
                    transCheckExecutorFactory.ofNullable(PayChannel.WX_PAY)
                                             .ifPresent(executor -> executor.rollbackTransResult(record.getTransNo(), record.getBatchNo(), error.getError()));
                }
            }
        });
    }
}
