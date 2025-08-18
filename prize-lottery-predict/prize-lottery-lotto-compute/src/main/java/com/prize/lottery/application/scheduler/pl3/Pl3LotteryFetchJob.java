package com.prize.lottery.application.scheduler.pl3;

import com.prize.lottery.domain.lottery.ability.LotteryDomainAbility;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.event.EventBus;
import com.prize.lottery.infrast.event.CalculatorEvent;
import com.prize.lottery.mapper.Pl3IcaiMapper;
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
public class Pl3LotteryFetchJob {

    private final Pl3IcaiMapper             pl3IcaiMapper;
    private final EventBus<CalculatorEvent> calculateProducer;
    private final LotteryDomainAbility      lotteryDomainAbility;

    @Async
    @Scheduled(cron = "0 1 7 * * *")
    public void againFetchLottery() {
        try {
            lotteryDomainAbility.fetchLotteryDetail(LotteryEnum.PL3);
        } catch (Exception e) {
            log.error("抓取排列三开奖数据异常:", e);
        }
    }

    @Async
    @Scheduled(cron = "0 30,40,50 21 * * *")
    @Scheduled(cron = "0 0,10,20,30 22 * * *")
    public void fetchLottery() {
        try {
            log.info("开始抓取排列三开奖数据...");
            String latest = lotteryDomainAbility.fetchLatestLottery(LotteryEnum.PL3);
            if (!StringUtils.hasText(latest)) {
                log.info("没有排列三最新开奖数据");
                return;
            }
            Period period = pl3IcaiMapper.latestPl3ICaiPeriod();
            if (period == null || period.getCalculated() == 1 || period.getPeriod().compareTo(latest) > 0) {
                log.info("没有排列三预测数据或者非最新开奖数据不进行命中计算.");
                return;
            }
            CalculatorEvent event = new CalculatorEvent(LotteryEnum.PL3, period.getPeriod());
            calculateProducer.publish(event);
        } catch (Exception e) {
            log.error("抓取排列三最新开奖信息异常:", e);
        }
    }

}
