package com.prize.lottery.application.scheduler.fc3d;

import com.prize.lottery.domain.lottery.ability.LotteryDomainAbility;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.event.EventBus;
import com.prize.lottery.infrast.event.CalculatorEvent;
import com.prize.lottery.mapper.Fc3dIcaiMapper;
import com.prize.lottery.value.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class Fc3dLotteryFetchJob {

    private final EventBus<CalculatorEvent> calculateProducer;
    private final Fc3dIcaiMapper            fc3dIcaiMapper;
    private final LotteryDomainAbility      lotteryDomainAbility;

    @Async
    @Scheduled(cron = "0 0 7 * * *")
    public void againFetchLottery() {
        try {
            lotteryDomainAbility.fetchLotteryDetail(LotteryEnum.FC3D);
        } catch (Exception e) {
            log.error("抓取福彩3D开奖数据异常:", e);
        }
    }

    @Async
    @Scheduled(cron = "0 38,48,58 21 * * *")
    @Scheduled(cron = "0 8,18 22 * * *")
    public void fetchLotteryHandle() {
        try {
            log.info("开始抓取福彩3D开奖数据...");
            String latest = lotteryDomainAbility.fetchLatestLottery(LotteryEnum.FC3D);
            if (!StringUtils.hasText(latest)) {
                log.info("没有福彩3D最新开奖数据...");
                return;
            }
            Period period = fc3dIcaiMapper.latestFc3dIcaiPeriod();
            if (period == null || period.getCalculated() == 1 || period.getPeriod().compareTo(latest) > 0) {
                log.info("没有福彩3D预测数据或者非最新开奖数据,不进行命中计算.");
                return;
            }
            //发送计算命中事件
            log.info("开始极端福彩3D第{}期预测命中数据.", period.getPeriod());
            CalculatorEvent event = new CalculatorEvent(LotteryEnum.FC3D, period.getPeriod());
            calculateProducer.publish(event);
        } catch (Exception e) {
            log.error("抓取福彩3D最新开奖信息异常:", e);
        }
    }

}
