package com.prize.lottery.application.scheduler.pl3;

import com.prize.lottery.application.service.INum3LayerService;
import com.prize.lottery.enums.LotteryEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Pl3LayerFetchJob {

    private final INum3LayerService num3LayerService;

    @Async
    @Scheduled(cron = "0 40,45,55 17 * * *")
    @Scheduled(cron = "0 5,15,25,35,40 18 * * *")
    public void schedule() {
        try {
            num3LayerService.fetchNum3Layer(LotteryEnum.PL3);
        } catch (Exception error) {
            log.error("抓取排列三分层预警预测异常:", error);
        }
    }

}
