package com.prize.lottery.application.scheduler;

import com.prize.lottery.application.command.IUserCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberExpireScheduler {

    private final static Integer TIMES = 100;
    private final static Integer LIMIT = 50;

    private final IUserCommandService userCommandService;

    /**
     * 每天凌晨00:10计算过期代金券
     */
    @Async
    @Scheduled(cron = "0 10 0 * * ?")
    public void schedule() {
        for (int i = 0; i < TIMES; i++) {
            Integer expiredSize = userCommandService.memberExpire(LIMIT);
            if (expiredSize < LIMIT) {
                break;
            }
        }
    }

}
