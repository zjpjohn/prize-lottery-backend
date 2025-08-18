package com.prize.lottery.application.scheduler.qlc;

import com.prize.lottery.infrast.config.OpenForecastConfigurer;
import com.prize.lottery.infrast.spider.open.OpenForecastFetcher;
import com.prize.lottery.infrast.utils.FetchTimeRanges;
import com.prize.lottery.mapper.QlcIcaiMapper;
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
public class QlcSpiderFetchJob {

    private final QlcIcaiMapper       qlcIcaiMapper;
    @Qualifier(OpenForecastConfigurer.QLC_FETCHER)
    private final OpenForecastFetcher qlcForecastFetcher;

    @Async
    @Scheduled(cron = "0 20 10,17,19 ? * 1,3,5")
    @Scheduled(cron = "0 20 12,21 ? * 2,4,6,7")
    public void fetchHandle() {
        try {
            Period period = qlcIcaiMapper.latestQlcICaiPeriod();
            if (period == null) {
                log.info("没有七乐彩历史预测数数据.");
                return;
            }
            String current = period.getPeriod();
            //已经开奖计算，抓取下一期数据
            if (period.getCalculated() == 1) {
                current = PeriodCalculator.qlcAddPeriod(current, 1);
            }
            int range = FetchTimeRanges.qlcRange(LocalDateTime.now());
            log.info("开始抓取七乐彩第{}期预测数据，抓取延迟时间范围{}秒", current, range);
            qlcForecastFetcher.fetchDelay(current, range);
        } catch (Exception e) {
            log.error("抓取七乐彩预测数据异常:", e);
        }
    }

}
