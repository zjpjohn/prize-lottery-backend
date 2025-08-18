package com.prize.lottery.application.scheduler;

import com.prize.lottery.application.command.executor.TransferMetricsExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferMetricsCalcScheduler {

    private final TransferMetricsExecutor transferMetricsExecutor;

    @Scheduled(cron = "0 30 0 * * ?")
    public void schedule() {
        transferMetricsExecutor.execute(LocalDate.now().minusDays(1));
    }

}
