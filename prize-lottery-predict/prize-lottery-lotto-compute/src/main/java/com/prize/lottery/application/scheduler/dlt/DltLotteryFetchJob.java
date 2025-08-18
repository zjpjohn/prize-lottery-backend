package com.prize.lottery.application.scheduler.dlt;

import com.prize.lottery.domain.lottery.ability.LotteryDomainAbility;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.event.EventBus;
import com.prize.lottery.infrast.event.CalculatorEvent;
import com.prize.lottery.mapper.DltIcaiMapper;
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
public class DltLotteryFetchJob {

    private final EventBus<CalculatorEvent> calculateProducer;
    private final DltIcaiMapper             dltIcaiMapper;
    private final LotteryDomainAbility      lotteryDomainAbility;

    /**
     * 重新抓取最新一期开奖数据
     */
    @Async
    @Scheduled(cron = "0 2 7 ? * 2,4,7")
    public void againFetchLottery() {
        try {
            lotteryDomainAbility.fetchLotteryDetail(LotteryEnum.DLT);
        } catch (Exception e) {
            log.error("抓取大乐透开奖数据异常:", e);
        }
    }

    /**
     * 每周一、三、六晚上21点35、40、45、50
     */
    @Async
    @Scheduled(cron = "0 40,50 21 ? * 1,3,6")
    @Scheduled(cron = "0 0,10,20 22 ? * 1,3,6")
    public void fetchLotteryHandle() {
        try {
            log.info("开始抓取大乐透开奖数据...");
            String latest = lotteryDomainAbility.fetchLatestLottery(LotteryEnum.DLT);
            if (!StringUtils.hasText(latest)) {
                log.info("没有大乐透最新开奖数据...");
                return;
            }
            Period period = dltIcaiMapper.latestDltICaiPeriod();
            if (period == null || period.getCalculated() == 1 || period.getPeriod().compareTo(latest) > 0) {
                log.info("没有大乐透预测数据或者非最新开奖数据,不进行命中计算.");
                return;
            }
            //发送计算命中事件
            CalculatorEvent event = new CalculatorEvent(LotteryEnum.DLT, period.getPeriod());
            calculateProducer.publish(event);
        } catch (Exception e) {
            log.error("抓取大乐透最新开奖信息异常:", e);
        }
    }

}
