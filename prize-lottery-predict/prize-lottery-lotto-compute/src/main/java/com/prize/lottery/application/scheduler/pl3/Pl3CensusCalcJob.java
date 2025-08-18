package com.prize.lottery.application.scheduler.pl3;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.event.EventBus;
import com.prize.lottery.infrast.event.StatsCalcEvent;
import com.prize.lottery.mapper.Pl3IcaiMapper;
import com.prize.lottery.value.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Pl3CensusCalcJob {

    private final Pl3IcaiMapper            pl3IcaiMapper;
    public final  EventBus<StatsCalcEvent> statisticProducer;

    @Async
    @Scheduled(cron = "0 22 17,18,19 * * *")
    public void censusHandle() {
        try {
            log.info("开始计算排列三预测数据统计...");
            Period period = pl3IcaiMapper.latestPl3ICaiPeriod();
            if (period == null || period.getCalculated() == 1) {
                log.info("没有排列三预测数据或数据已开奖计算.");
                return;
            }
            StatsCalcEvent event = new StatsCalcEvent(LotteryEnum.PL3, period.getPeriod());
            statisticProducer.publish(event);
            log.info("计算排列三第{}期预测数据统计.", period.getPeriod());
        } catch (Exception e) {
            log.info("计算排列三预测数据统计异常:", e);
        }
    }

}
