package com.prize.lottery.application.consumer;

import com.prize.lottery.application.command.IPayTransferCommandService;
import com.prize.lottery.application.consumer.event.PayTransferEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayTransferHandler {

    private final IPayTransferCommandService payTransferCommandService;

    /**
     * 提现事件处理
     *
     * @param event 提现请求事件
     */
    @EventListener
    public void onHandle(PayTransferEvent event) {
        payTransferCommandService.payTransfer(event);
    }
}
