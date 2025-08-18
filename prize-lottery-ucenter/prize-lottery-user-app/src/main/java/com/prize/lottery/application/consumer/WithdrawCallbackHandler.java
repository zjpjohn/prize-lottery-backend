package com.prize.lottery.application.consumer;

import com.prize.lottery.application.command.IWithdrawCommandService;
import com.prize.lottery.application.consumer.event.TransCallbackEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WithdrawCallbackHandler {

    private final IWithdrawCommandService withdrawCommandService;

    @EventListener
    public void handle(TransCallbackEvent event) {
        withdrawCommandService.withdrawRollback(event);
    }

}
