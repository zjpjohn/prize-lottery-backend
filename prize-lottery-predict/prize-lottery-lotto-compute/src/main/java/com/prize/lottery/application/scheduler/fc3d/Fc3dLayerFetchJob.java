package com.prize.lottery.application.scheduler.fc3d;

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
public class Fc3dLayerFetchJob {

    private final INum3LayerService num3LayerService;

    @Async
    @Scheduled(cron = "0 40,50 17 * * *")
    @Scheduled(cron = "0 10,20,20,40,50 18 * * *")
    public void schedule() {
        try {
            num3LayerService.fetchNum3Layer(LotteryEnum.FC3D);
        } catch (Exception error) {
            log.error("抓取福彩3D分层预警预测异常:", error);
        }
    }
}
