package com.prize.lottery.application.scheduler.qlc;

import com.prize.lottery.domain.lottery.ability.LotteryDomainAbility;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.event.EventBus;
import com.prize.lottery.infrast.event.CalculatorEvent;
import com.prize.lottery.mapper.QlcIcaiMapper;
import com.prize.lottery.value.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class QlcLotteryFetchJob {

    private final EventBus<CalculatorEvent> calculateProducer;
    private final QlcIcaiMapper        qlcIcaiMapper;
    private final LotteryDomainAbility lotteryDomainAbility;

    @Async
    @Scheduled(cron = "0 3 7 ? * 2,4,6")
    public void againFetchLottery() {
        try {
            lotteryDomainAbility.fetchLotteryDetail(LotteryEnum.QLC);
        } catch (Exception e) {
            log.error("抓取七乐彩开奖数据异常:", e);
        }
    }

    @Async
    @Scheduled(cron = "0 40,50 21 ? * 1,3,5")
    @Scheduled(cron = "0 0,10,20 22 ? * 1,3,5")
    public void fetchLottery() {
        try {
            log.info("开始抓取七乐彩开奖数据...");
            String latest = lotteryDomainAbility.fetchLatestLottery(LotteryEnum.QLC);
            if (StringUtils.isBlank(latest)) {
                log.info("没有七乐彩最新开奖数据...");
                return;
            }
            Period period = qlcIcaiMapper.latestQlcICaiPeriod();
            if (period == null || period.getCalculated() == 1 || period.getPeriod().compareTo(latest) > 0) {
                log.info("没有七乐彩预测数据或非最新开奖数据,不进行命中计算.");
                return;
            }
            //发送计算命中事件
            CalculatorEvent event = new CalculatorEvent(LotteryEnum.QLC, period.getPeriod());
            calculateProducer.publish(event);
        } catch (Exception e) {
            log.error("抓取七乐彩最新开奖信息异常:", e);
        }
    }

}
