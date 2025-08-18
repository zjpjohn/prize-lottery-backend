package com.prize.lottery.application.scheduler;

import com.prize.lottery.application.command.IUserStatsCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserReportScheduler {

    private final IUserStatsCommandService commandService;

    @Async
    @Scheduled(cron = "0 5 0 * * ?")
    public void schedule() {
        commandService.dateReport(LocalDate.now().minusDays(1));
    }

}
