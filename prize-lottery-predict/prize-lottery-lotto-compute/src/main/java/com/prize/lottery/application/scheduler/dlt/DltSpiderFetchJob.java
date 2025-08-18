package com.prize.lottery.application.scheduler.dlt;

import com.prize.lottery.infrast.spider.open.OpenForecastFetcher;
import com.prize.lottery.infrast.utils.FetchTimeRanges;
import com.prize.lottery.mapper.DltIcaiMapper;
import com.prize.lottery.utils.PeriodCalculator;
import com.prize.lottery.value.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class DltSpiderFetchJob {

    private final DltIcaiMapper       dltIcaiMapper;
    private final OpenForecastFetcher dltForecastFetcher;

    @Async
    @Scheduled(cron = "0 20 10,17,19 ? * 1,3,6")
    @Scheduled(cron = "0 20 12,21 ? * 2,4,5,7")
    public void fetchForecastHandle() {
        try {
            Period period = dltIcaiMapper.latestDltICaiPeriod();
            if (period == null) {
                log.info("没有大乐透历史预测数数据.");
                return;
            }
            String current = period.getPeriod();
            //已经开奖计算，抓取下一期数据
            if (period.getCalculated() == 1) {
                current = PeriodCalculator.dltAddPeriod(current, 1);
            }
            int range = FetchTimeRanges.dltRange(LocalDateTime.now());
            log.info("开始抓取大乐透第{}期预测数据，抓取延迟时间范围{}秒", current, range);
            dltForecastFetcher.fetchDelay(current, range);
        } catch (Exception e) {
            log.error("抓取大乐透预测数据异常:", e);
        }
    }

}
