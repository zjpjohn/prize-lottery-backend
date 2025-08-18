package com.prize.lottery.application.scheduler.pl3;

import com.prize.lottery.domain.lottery.ability.LotteryDomainAbility;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.Pl3IcaiMapper;
import com.prize.lottery.value.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Pl3TrialFetchJob {

    private final Pl3IcaiMapper        pl3IcaiMapper;
    private final LotteryDomainAbility lotteryDomainAbility;

    /**
     * 每天17点1、5、10、15、20分抓取
     */
    @Async
    @Scheduled(cron = "0 5,10,15,20,25,35,45,55 17 * * *")
    @Scheduled(cron = "0 0,5,10,15 18 * * *")
    public void fetchTrailHandle() {
        try {
            Period period = pl3IcaiMapper.latestPl3ICaiPeriod();
            if (period == null || period.getCalculated() == 1) {
                log.info("没有预测数据或数据已计算，请手动抓取.");
                return;
            }
            lotteryDomainAbility.fetchLotteryTrial(LotteryEnum.PL3, period.getPeriod());
        } catch (Exception e) {
            log.error("抓取排列三试机号异常:", e);
        }
    }

}
