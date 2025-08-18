package com.prize.lottery.application.scheduler;

import com.prize.lottery.application.command.IVoucherCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class VoucherExpireScheduler {

    private final static Integer TIMES = 100;
    private final static Integer LIMIT = 50;

    private final IVoucherCommandService voucherCommandService;

    /**
     * 每天凌晨00:30计算过期代金券
     */
    @Async
    @Scheduled(cron = "0 30 0 * * ?")
    public void schedule() {
        for (int i = 0; i < TIMES; i++) {
            Integer expiredSize = voucherCommandService.voucherExpire(LIMIT);
            if (expiredSize < LIMIT) {
                break;
            }
        }
    }
}
