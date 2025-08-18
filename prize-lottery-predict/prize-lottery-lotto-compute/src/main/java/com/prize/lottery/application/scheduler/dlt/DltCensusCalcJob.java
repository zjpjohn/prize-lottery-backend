package com.prize.lottery.application.scheduler.dlt;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.event.EventBus;
import com.prize.lottery.infrast.event.StatsCalcEvent;
import com.prize.lottery.mapper.DltIcaiMapper;
import com.prize.lottery.value.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DltCensusCalcJob {

    private final DltIcaiMapper            dltIcaiMapper;
    private final EventBus<StatsCalcEvent> statisticProducer;

    @Async
    @Scheduled(cron = "0 25 17,19 ? * 1,3,6")
    @Scheduled(cron = "0 25 21 ? * 2,4,5,7")
    public void censusHandle() {
        try {
            log.info("开始计算大乐透预测数据统计...");
            Period period = dltIcaiMapper.latestDltICaiPeriod();
            if (period == null || period.getCalculated() == 1) {
                log.info("没有大乐透预测数据或数据已开奖计算.");
                return;
            }
            StatsCalcEvent event = new StatsCalcEvent(LotteryEnum.DLT, period.getPeriod());
            statisticProducer.publish(event);
            log.info("计算大乐透第{}期预测数据统计.", period.getPeriod());
        } catch (Exception e) {
            log.error("计算大乐透预测数据统计异常:", e);
        }
    }

}
