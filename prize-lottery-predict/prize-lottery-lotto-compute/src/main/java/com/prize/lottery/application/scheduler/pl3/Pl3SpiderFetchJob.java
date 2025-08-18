package com.prize.lottery.application.scheduler.pl3;

import com.prize.lottery.infrast.config.OpenForecastConfigurer;
import com.prize.lottery.infrast.spider.open.OpenForecastFetcher;
import com.prize.lottery.infrast.utils.FetchTimeRanges;
import com.prize.lottery.mapper.Pl3IcaiMapper;
import com.prize.lottery.utils.PeriodCalculator;
import com.prize.lottery.value.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class Pl3SpiderFetchJob {

    private final Pl3IcaiMapper       pl3IcaiMapper;
    @Qualifier(OpenForecastConfigurer.PL3_FETCHER)
    private final OpenForecastFetcher pl3ForecastFetcher;

    @Async
    @Scheduled(cron = "0 20 10,17,19 * * *")
    public void fetchForecastHandle() {
        try {
            Period period = pl3IcaiMapper.latestPl3ICaiPeriod();
            if (period == null) {
                log.info("没有排列三历史预测数据.");
                return;
            }
            String current = period.getPeriod();
            if (period.getCalculated() == 1) {
                current = PeriodCalculator.pl3AddPeriod(current, 1);
            }
            int range = FetchTimeRanges.n3Range(LocalDateTime.now());
            log.info("开始抓取排列三第{}期预测数据，抓取延迟时间范围{}秒", current, range);
            pl3ForecastFetcher.fetchDelay(current, range);
        } catch (Exception e) {
            log.error("抓取排列三预测数据异常:", e);
        }
    }

}
