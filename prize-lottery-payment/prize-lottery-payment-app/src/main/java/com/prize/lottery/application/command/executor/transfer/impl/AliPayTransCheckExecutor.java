package com.prize.lottery.application.command.executor.transfer.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.event.core.publish.GenericEvent;
import com.cloud.arch.event.publisher.DomainEventPublisher;
import com.cloud.arch.executor.EnumerableExecutorFactory;
import com.prize.lottery.application.command.event.TransCallbackEvent;
import com.prize.lottery.application.command.executor.transfer.TransCheckExecutor;
import com.prize.lottery.domain.transfer.model.aggregate.PayTransferOneDo;
import com.prize.lottery.domain.transfer.repository.IPayTransferRepository;
import com.prize.lottery.pay.PayChannel;
import com.prize.lottery.transfer.FundTransferExecutor;
import com.prize.lottery.transfer.TransDetailRequest;
import com.prize.lottery.transfer.TransDetailResponse;
import com.prize.lottery.transfer.TransferState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class AliPayTransCheckExecutor implements TransCheckExecutor {

    private final IPayTransferRepository                                      payTransferRepository;
    private final EnumerableExecutorFactory<PayChannel, FundTransferExecutor> fundTransferExecutorFactory;

    @Override
    public PayChannel bizIndex() {
        return PayChannel.ALI_PAY;
    }

    @Override
    @Transactional
    public void checkTransResult(String transNo, String bachNo) {
        FundTransferExecutor transferExecutor = fundTransferExecutorFactory.ofNullable(PayChannel.WX_PAY).orElse(null);
        if (transferExecutor == null) {
            return;
        }
        TransDetailRequest                request   = new TransDetailRequest(transNo);
        TransDetailResponse               response  = transferExecutor.query(request);
        Aggregate<Long, PayTransferOneDo> aggregate = payTransferRepository.ofOne(response.getTransNo());
        if (aggregate == null || !aggregate.getRoot().doing()) {
            return;
        }
        PayTransferOneDo   transfer = aggregate.getRoot();
        TransCallbackEvent event    = transfer.check(response.getState(), response.getLatestTime(), response.getFailReason());
        payTransferRepository.saveOne(aggregate);
        if (event != null) {
            GenericEvent genericEvent = GenericEvent.create(event, "cloud-lottery-user-topic", event.getScene());
            DomainEventPublisher.publish(genericEvent);
        }
    }

    @Override
    @Transactional
    public void rollbackTransResult(String transNo, String batchNo, String reason) {
        Aggregate<Long, PayTransferOneDo> aggregate = payTransferRepository.ofOne(transNo);
        if (aggregate == null || !aggregate.getRoot().doing()) {
            return;
        }
        PayTransferOneDo   transfer = aggregate.getRoot();
        TransCallbackEvent event    = transfer.check(TransferState.FAIL, LocalDateTime.now(), reason);
        payTransferRepository.saveOne(aggregate);
        //发布失败关单回调事件
        GenericEvent genericEvent = GenericEvent.create(event, "cloud-lottery-user-topic", event.getScene());
        DomainEventPublisher.publish(genericEvent);
    }

}
