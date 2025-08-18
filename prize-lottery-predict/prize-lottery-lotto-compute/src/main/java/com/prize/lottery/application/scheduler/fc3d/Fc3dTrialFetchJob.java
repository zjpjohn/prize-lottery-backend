package com.prize.lottery.application.scheduler.fc3d;

import com.prize.lottery.domain.lottery.ability.LotteryDomainAbility;
import com.prize.lottery.enums.LotteryEnum;
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
public class Fc3dTrialFetchJob {

    private final Fc3dIcaiMapper       fc3dIcaiMapper;
    private final LotteryDomainAbility lotteryDomainAbility;

    /**
     * 每天17点1、5、10、15、20分抓取
     */
    @Async
    @Scheduled(cron = "0 5,10,15,20,25,35,45,55 17 * * *")
    @Scheduled(cron = "0 0,5,10,15 18 * * *")
    public void fetchTrialHandle() {
        try {
            Period period = fc3dIcaiMapper.latestFc3dIcaiPeriod();
            if (period == null || period.getCalculated() == 1) {
                log.info("没有福彩3D预测数据或数据已经计算，请手动抓取.");
                return;
            }
            lotteryDomainAbility.fetchLotteryTrial(LotteryEnum.FC3D, period.getPeriod());
        } catch (Exception e) {
            log.error("抓取福彩3D试机号异常:", e);
        }
    }
}
