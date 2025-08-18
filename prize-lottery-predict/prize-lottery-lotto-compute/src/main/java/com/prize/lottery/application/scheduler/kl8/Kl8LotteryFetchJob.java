package com.prize.lottery.application.scheduler.kl8;

import com.prize.lottery.domain.lottery.ability.LotteryDomainAbility;
import com.prize.lottery.domain.omit.ability.LotteryOmitAbility;
import com.prize.lottery.enums.LotteryEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Kl8LotteryFetchJob {

    private final LotteryDomainAbility lotteryDomainAbility;
    private final LotteryOmitAbility   lotteryOmitAbility;

    @Async
    @Scheduled(cron = "0 5 7 * * *")
    public void againFetchLottery() {
        try {
            lotteryDomainAbility.fetchLotteryDetail(LotteryEnum.KL8);
        } catch (Exception e) {
            log.error("抓取快乐8开奖数据异常:", e);
        }
    }

    @Async
    @Scheduled(cron = "0 50,55 21 * * *")
    @Scheduled(cron = "0 0,10,20 22 * * *")
    public void fetchLottery() {
        try {
            log.info("开始抓取快乐8开奖数据...");
            String period = lotteryDomainAbility.fetchLatestLottery(LotteryEnum.KL8);
            if (StringUtils.isNotBlank(period)) {
                log.info("已抓取到快乐8最新奖号，开始进行遗漏计算...");
                lotteryOmitAbility.calcOmit(LotteryEnum.KL8, period);
            }
        } catch (Exception e) {
            log.error("抓取快乐8最新开奖数据异常:", e);
        }
    }

}
