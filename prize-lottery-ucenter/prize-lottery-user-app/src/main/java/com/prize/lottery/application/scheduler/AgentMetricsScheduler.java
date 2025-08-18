package com.prize.lottery.application.scheduler;

import com.prize.lottery.application.command.IAgentAcctCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class AgentMetricsScheduler {

    private final IAgentAcctCommandService agentAcctCommandService;

    /**
     * 每天凌晨20点计算前一天的流量主收益数据
     */
    @Async
    @Scheduled(cron = "0 20 0 * * ?")
    public void schedule() {
        LocalDate date = LocalDate.now().minusDays(1);
        agentAcctCommandService.agentMetrics(date);
    }

}
