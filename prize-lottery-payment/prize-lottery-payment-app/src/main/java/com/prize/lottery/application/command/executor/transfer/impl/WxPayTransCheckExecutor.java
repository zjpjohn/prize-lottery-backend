package com.prize.lottery.application.command.executor.transfer.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.event.core.publish.GenericEvent;
import com.cloud.arch.event.publisher.DomainEventPublisher;
import com.cloud.arch.executor.EnumerableExecutorFactory;
import com.prize.lottery.application.command.event.TransCallbackEvent;
import com.prize.lottery.application.command.executor.transfer.TransCheckExecutor;
import com.prize.lottery.domain.transfer.model.aggregate.PayTransferBatchDo;
import com.prize.lottery.domain.transfer.repository.IPayTransferRepository;
import com.prize.lottery.pay.PayChannel;
import com.prize.lottery.transfer.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class WxPayTransCheckExecutor implements TransCheckExecutor {

    private final EnumerableExecutorFactory<PayChannel, FundTransferExecutor> fundTransferExecutorFactory;
    private final IPayTransferRepository                                      payTransferRepository;

    @Override
    public PayChannel bizIndex() {
        return PayChannel.WX_PAY;
    }

    @Override
    @Transactional
    public void checkTransResult(String transNo, String batchNo) {
        FundTransferExecutor transferExecutor = fundTransferExecutorFactory.ofNullable(PayChannel.WX_PAY).orElse(null);
        if (transferExecutor == null) {
            return;
        }
        TransBatchRequest  batchRequest  = new TransBatchRequest(batchNo);
        TransBatchResponse batchResponse = transferExecutor.query(batchRequest);
        TransBatchState    batchState    = batchResponse.getState();
        //已受理或正在处理中的批次不再进行下一步处理
        if (batchState == TransBatchState.ACCEPTED || batchState == TransBatchState.PROCESSING) {
            return;
        }
        //批次处于完成状态，查询转账详情信息
        TransDetailResponse detailResponse = null;
        if (batchState == TransBatchState.FINISHED) {
            TransDetailRequest detailRequest = new TransDetailRequest(transNo, batchNo);
            detailResponse = transferExecutor.query(detailRequest);
        }
        Aggregate<Long, PayTransferBatchDo> aggregate = payTransferRepository.batchOfBatchNo(batchNo);
        if (aggregate == null || !aggregate.getRoot().doing()) {
            return;
        }
        PayTransferBatchDo transferBatch = aggregate.getRoot();
        TransCallbackEvent event         = transferBatch.check(batchResponse, detailResponse);
        payTransferRepository.saveBatch(aggregate);
        if (event != null) {
            GenericEvent genericEvent = GenericEvent.create(event, "cloud-lottery-user-topic", event.getScene());
            DomainEventPublisher.publish(genericEvent);
        }
    }

    @Override
    @Transactional
    public void rollbackTransResult(String tranNo, String batchNo, String reason) {
        Aggregate<Long, PayTransferBatchDo> aggregate = payTransferRepository.batchOfBatchNo(batchNo);
        if (aggregate == null || !aggregate.getRoot().doing()) {
            return;
        }
        PayTransferBatchDo transferBatch = aggregate.getRoot();
        TransCallbackEvent event         = transferBatch.rollback(LocalDateTime.now(), reason);
        payTransferRepository.saveBatch(aggregate);
        //发布失败关单回调事件
        GenericEvent genericEvent = GenericEvent.create(event, "cloud-lottery-user-topic", event.getScene());
        DomainEventPublisher.publish(genericEvent);
    }
}
