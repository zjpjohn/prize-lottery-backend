package com.prize.lottery.application.scheduler;

import com.prize.lottery.application.command.executor.OrderMetricsExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderMetricsCalcScheduler {

    private final OrderMetricsExecutor orderMetricsExecutor;

    @Scheduled(cron = "0 35 0 * * ?")
    public void schedule() {
        LocalDate lastDate = LocalDate.now().minusDays(1);
        orderMetricsExecutor.execute(lastDate);
    }

}
