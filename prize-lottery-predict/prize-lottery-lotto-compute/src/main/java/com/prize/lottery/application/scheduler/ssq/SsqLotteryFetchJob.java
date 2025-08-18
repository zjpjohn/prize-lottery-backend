package com.prize.lottery.application.scheduler.ssq;

import com.prize.lottery.domain.lottery.ability.LotteryDomainAbility;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.event.EventBus;
import com.prize.lottery.infrast.event.CalculatorEvent;
import com.prize.lottery.mapper.SsqIcaiMapper;
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
public class SsqLotteryFetchJob {

    private final SsqIcaiMapper             ssqIcaiMapper;
    private final EventBus<CalculatorEvent> calculateProducer;
    private final LotteryDomainAbility      lotteryDomainAbility;

    @Async
    @Scheduled(cron = "0 4 7 ? * 1,3,5")
    public void againFetchLottery() {
        try {
            lotteryDomainAbility.fetchLotteryDetail(LotteryEnum.SSQ);
        } catch (Exception e) {
            log.error("抓取上色球开奖数据异常:", e);
        }
    }

    @Async
    @Scheduled(cron = "0 35,45,55 21 ? * 2,4,7")
    @Scheduled(cron = "0 5,15,25 22 ? * 2,4,7")
    public void fetchLotteryHandle() {
        try {
            log.info("开始抓取双色球开奖数据...");
            String latest = lotteryDomainAbility.fetchLatestLottery(LotteryEnum.SSQ);
            if (!StringUtils.hasText(latest)) {
                log.info("没有双色球最新开奖数据...");
                return;
            }
            Period period = ssqIcaiMapper.latestSsqICaiPeriod();
            if (period == null || period.getCalculated() == 1 || period.getPeriod().compareTo(latest) > 0) {
                log.info("没有双色球预测数据或者非最新开奖数据,不进行命中计算.");
                return;
            }
            //发送计算命中事件
            CalculatorEvent event = new CalculatorEvent(LotteryEnum.SSQ, period.getPeriod());
            calculateProducer.publish(event);
        } catch (Exception e) {
            log.error("抓取双色球最新开奖信息异常:", e);
        }
    }

}
