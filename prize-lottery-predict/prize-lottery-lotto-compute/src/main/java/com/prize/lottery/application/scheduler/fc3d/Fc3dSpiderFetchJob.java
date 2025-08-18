package com.prize.lottery.application.scheduler.fc3d;

import com.prize.lottery.infrast.config.OpenForecastConfigurer;
import com.prize.lottery.infrast.spider.open.OpenForecastFetcher;
import com.prize.lottery.infrast.utils.FetchTimeRanges;
import com.prize.lottery.mapper.Fc3dIcaiMapper;
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
public class Fc3dSpiderFetchJob {

    private final Fc3dIcaiMapper      fc3dIcaiMapper;
    @Qualifier(OpenForecastConfigurer.FC3D_FETCHER)
    private final OpenForecastFetcher fc3dForecastFetcher;

    @Async
    @Scheduled(cron = "0 20 10,17,19 * * *")
    public void fetchForecastHandle() {
        try {
            Period period = fc3dIcaiMapper.latestFc3dIcaiPeriod();
            if (period == null) {
                log.info("没有福彩3D历史预测数数据.");
                return;
            }
            String current = period.getPeriod();
            //已经开奖计算，抓取下一期数据
            if (period.getCalculated() == 1) {
                current = PeriodCalculator.fc3dAddPeriod(current, 1);
            }
            int range = FetchTimeRanges.n3Range(LocalDateTime.now());
            log.info("开始抓取福彩3D第{}期预测数据，抓取延迟时间范围{}秒", current, range);
            fc3dForecastFetcher.fetchDelay(current, range);
        } catch (Exception e) {
            log.error("抓取福彩3D预测数据异常:", e);
        }
    }

}
