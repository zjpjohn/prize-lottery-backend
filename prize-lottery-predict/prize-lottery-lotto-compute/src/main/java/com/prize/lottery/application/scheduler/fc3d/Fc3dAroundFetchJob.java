package com.prize.lottery.application.scheduler.fc3d;

import com.prize.lottery.infrast.spider.around.Fc3dAroundSpider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Fc3dAroundFetchJob {

    private final Fc3dAroundSpider fc3dAroundSpider;

    @Async
    @Scheduled(cron = "0 0,10,20 8 * * *")
    public void fetchAround() {
        try {
            fc3dAroundSpider.fetchAround(null);
        } catch (Exception e) {
            log.error("抓取福彩3D绕胆图异常:", e);
        }
    }

}
