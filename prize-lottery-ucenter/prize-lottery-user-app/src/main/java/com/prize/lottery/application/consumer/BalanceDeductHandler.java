package com.prize.lottery.application.consumer;

import com.prize.lottery.application.command.executor.UserBalanceDeductExecutor;
import com.prize.lottery.application.consumer.event.BalanceDeductEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BalanceDeductHandler {

    private final UserBalanceDeductExecutor balanceDeductExecutor;

    /**
     * 扣减账户金币
     *
     * @param command 扣减账户金币事件
     */
    @EventListener
    public void onHandle(BalanceDeductEvent command) {
        balanceDeductExecutor.execute(command);
    }

}
