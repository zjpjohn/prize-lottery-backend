package com.prize.lottery.application.scheduler.ssq;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.event.EventBus;
import com.prize.lottery.infrast.event.StatsCalcEvent;
import com.prize.lottery.mapper.SsqIcaiMapper;
import com.prize.lottery.value.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SsqCensusCalcJob {

    private final SsqIcaiMapper            ssqIcaiMapper;
    private final EventBus<StatsCalcEvent> statisticProducer;

    @Async
    @Scheduled(cron = "0 25 17,19 ? * 2,4,7")
    @Scheduled(cron = "0 25 21 ? * 1,3,5,6")
    public void censusHandle() {
        try {
            log.info("开始计算双色球预测数据统计...");
            Period period = ssqIcaiMapper.latestSsqICaiPeriod();
            if (period == null || period.getCalculated() == 1) {
                log.info("没有预测数据或数据已开奖计算.");
                return;
            }
            StatsCalcEvent event = new StatsCalcEvent(LotteryEnum.SSQ, period.getPeriod());
            statisticProducer.publish(event);
            log.info("计算双色球第{}期预测数据统计.", period.getPeriod());
        } catch (Exception e) {
            log.error("计算双色球预测数据统计异常:", e);
        }
    }

}
