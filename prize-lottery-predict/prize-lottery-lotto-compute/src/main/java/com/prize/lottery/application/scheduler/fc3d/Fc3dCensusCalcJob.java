package com.prize.lottery.application.scheduler.fc3d;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.event.EventBus;
import com.prize.lottery.infrast.event.StatsCalcEvent;
import com.prize.lottery.mapper.Fc3dIcaiMapper;
import com.prize.lottery.value.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Fc3dCensusCalcJob {

    private final EventBus<StatsCalcEvent> statisticProducer;
    private final Fc3dIcaiMapper           fc3dIcaiMapper;

    @Async
    @Scheduled(cron = "0 22 17,18,19 * * *")
    public void censusHandle() {
        try {
            log.info("开始计算福彩3D预测数据统计...");
            Period period = fc3dIcaiMapper.latestFc3dIcaiPeriod();
            if (period == null || period.getCalculated() == 1) {
                log.info("没有福彩3D预测数据或数据已开奖计算.");
                return;
            }
            StatsCalcEvent event = new StatsCalcEvent(LotteryEnum.FC3D, period.getPeriod());
            statisticProducer.publish(event);
            log.info("计算福彩3D第{}期预测数据统计.", period.getPeriod());
        } catch (Exception e) {
            log.error("计算福彩3D预测数据统计异常:", e);
        }
    }

}
