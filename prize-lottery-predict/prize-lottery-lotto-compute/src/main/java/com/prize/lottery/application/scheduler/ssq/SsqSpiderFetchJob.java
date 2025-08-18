package com.prize.lottery.application.scheduler.ssq;

import com.prize.lottery.infrast.config.OpenForecastConfigurer;
import com.prize.lottery.infrast.spider.open.OpenForecastFetcher;
import com.prize.lottery.infrast.utils.FetchTimeRanges;
import com.prize.lottery.mapper.SsqIcaiMapper;
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
public class SsqSpiderFetchJob {

    private final SsqIcaiMapper       ssqIcaiMapper;
    @Qualifier(OpenForecastConfigurer.SSQ_FETCHER)
    private final OpenForecastFetcher ssqForecastFetcher;

    @Async
    @Scheduled(cron = "0 20 10,17,19 ? * 2,4,7")
    @Scheduled(cron = "0 20 12,21 ? * 1,3,5,6")
    public void fetchHandle() {
        try {
            Period period = ssqIcaiMapper.latestSsqICaiPeriod();
            if (period == null) {
                log.info("没有双色球历史预测数数据.");
                return;
            }
            String current = period.getPeriod();
            //已经开奖计算，抓取下一期数据
            if (period.getCalculated() == 1) {
                current = PeriodCalculator.ssqAddPeriod(current, 1);
            }
            int range = FetchTimeRanges.ssqRange(LocalDateTime.now());
            log.info("开始抓取双色球第{}期预测数据，抓取延迟时间范围{}秒", current, range);
            ssqForecastFetcher.fetchDelay(current, range);
        } catch (Exception e) {
            log.error("抓取双色球预测数据异常:", e);
        }
    }

}
