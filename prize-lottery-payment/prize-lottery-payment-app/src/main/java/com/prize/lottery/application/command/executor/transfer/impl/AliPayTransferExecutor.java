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
import com.prize.lottery.domain.transfer.model.aggregate.PayTransferOneDo;
import com.prize.lottery.domain.transfer.model.aggregate.TransferAuditDo;
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
public class AliPayTransferExecutor implements TransferExecutor {

    private final Executor                                                  executor;
    private final PayTransferFactory                                        payTransferFactory;
    private final TransferAuditFactory                                      transferAuditFactory;
    private final IPayTransferRepository                                    payTransferRepository;
    private final ITransferAuditRepository                                  transferAuditRepository;
    private final PayTransferExecutorAbility                                payTransferExecutorAbility;
    private final EnumerableExecutorFactory<PayChannel, TransCheckExecutor> transCheckExecutorFactory;

    public AliPayTransferExecutor(@Qualifier("asyncPayExecutor") Executor executor,
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
        return PayChannel.ALI_PAY;
    }

    @Override
    public void payTransfer(PayTransferEvent event) {
        PayTransferOneDo aliPayTransfer = payTransferFactory.createAliPay(event);
        AggregateFactory.create(aliPayTransfer).save(payTransferRepository::saveOne);
        //直接异步发起支付转账
        if (aliPayTransfer.getAudit() == AuditState.NONE) {
            this.asyncAliPayTransfer(aliPayTransfer);
            return;
        }
        //需审核后才进行扣款
        TransferAuditDo transferAudit = transferAuditFactory.initialize(aliPayTransfer.getTransNo());
        transferAuditRepository.save(transferAudit);
        //发送提现审核回调事件
        TransCallbackEvent auditEvent   = aliPayTransfer.auditEvent();
        GenericEvent       genericEvent = GenericEvent.create(auditEvent, "cloud-lottery-user-topic", auditEvent.getScene());
        DomainEventPublisher.publish(genericEvent);
    }

    @Override
    public void auditTransfer(String transNo, AuditState state, String reason) {
        Aggregate<Long, PayTransferOneDo> aggregate = payTransferRepository.ofOne(transNo);
        PayTransferOneDo                  transfer  = aggregate.getRoot();
        transfer.audit(state, reason);
        payTransferRepository.saveOne(aggregate);
        if (state == AuditState.ADOPTED) {
            //审核通过后直接异步发起支付
            this.asyncAliPayTransfer(transfer);
            return;
        }
        if (state == AuditState.REJECTED) {
            //审核不通过，推送关单回调事件
            TransCallbackEvent event        = transfer.callbackEvent();
            GenericEvent       genericEvent = GenericEvent.create(event, "cloud-lottery-user-topic", event.getScene());
            DomainEventPublisher.publish(genericEvent);
        }
    }

    /**
     * 异步调起支付宝转账接口
     */
    private void asyncAliPayTransfer(PayTransferOneDo transfer) {
        executor.execute(() -> {
            try {
                payTransferExecutorAbility.executeAliPay(transfer);
            } catch (PayRequestException error) {
                log.error(error.getMessage(), error);
                //调用支付错误，回退转账
                if (error.isRollback()) {
                    transCheckExecutorFactory.ofNullable(PayChannel.ALI_PAY)
                                             .ifPresent(executor -> executor.rollbackTransResult(transfer.getTransNo(), transfer.getBatchNo(), error.getError()));
                }
            }
        });
    }
}
