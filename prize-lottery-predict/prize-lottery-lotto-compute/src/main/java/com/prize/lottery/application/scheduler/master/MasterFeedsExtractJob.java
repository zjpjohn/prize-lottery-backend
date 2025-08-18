package com.prize.lottery.application.scheduler.master;

import com.prize.lottery.domain.feeds.ability.MasterFeedsDomainAbility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MasterFeedsExtractJob {

    private final MasterFeedsDomainAbility masterFeedsDomainAbility;

    @Async
    @Scheduled(cron = "0 30 6 * * *")
    public void extractFeeds() {
        log.info("提取首页预测专家信息流...");
        masterFeedsDomainAbility.extract();
    }

}
