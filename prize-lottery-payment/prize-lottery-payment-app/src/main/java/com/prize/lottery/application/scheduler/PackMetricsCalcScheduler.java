package com.prize.lottery.application.scheduler;

import com.prize.lottery.application.command.executor.PackMetricsExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class PackMetricsCalcScheduler {

    private final PackMetricsExecutor packMetricsExecutor;

    @Scheduled(cron = "0 20 0 * * ?")
    public void schedule() {
        packMetricsExecutor.execute(LocalDate.now().minusDays(1));
    }

}
