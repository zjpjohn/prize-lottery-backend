package com.prize.lottery.application.scheduler.pl5;

import com.prize.lottery.domain.lottery.ability.LotteryDomainAbility;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.event.EventBus;
import com.prize.lottery.infrast.event.CalculatorEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class Pl5LotteryFetchJob {

    private final EventBus<CalculatorEvent> calculateProducer;
    private final LotteryDomainAbility      lotteryDomainAbility;

    @Async
    @Scheduled(cron = "0 1 7 * * *")
    public void againFetchLottery() {
        try {
            lotteryDomainAbility.fetchLotteryDetail(LotteryEnum.PL5);
        } catch (Exception e) {
            log.error("抓取排列五开奖数据异常:", e);
        }
    }

    @Async
    @Scheduled(cron = "0 40,50,55 21 * * *")
    @Scheduled(cron = "0 0,10,20,30 22 * * *")
    public void fetchLottery() {
        try {
            log.info("开始抓取排列五开奖数据...");
            String latest = lotteryDomainAbility.fetchLatestLottery(LotteryEnum.PL5);
            if (!StringUtils.hasText(latest)) {
                log.info("没有排列五最新开奖数据");
                return;
            }
            CalculatorEvent event = new CalculatorEvent(LotteryEnum.PL5, latest);
            calculateProducer.publish(event);
        } catch (Exception e) {
            log.error("抓取排列五最新开奖信息异常:", e);
        }
    }

}
