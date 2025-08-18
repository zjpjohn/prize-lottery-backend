package com.prize.lottery.application.schedule;

import com.prize.lottery.domain.notify.ability.NotifyPushDomainService;
import com.prize.lottery.domain.notify.repository.INotifyAppRepository;
import com.prize.lottery.domain.notify.repository.INotifyInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import static com.prize.lottery.infrast.config.ExecutorConfigurer.PUSH_EXECUTOR;


@Slf4j
@Component
@RequiredArgsConstructor
public class TagBasedPushScheduler {

    @Qualifier(PUSH_EXECUTOR)
    private final ThreadPoolTaskExecutor  executor;
    private final INotifyAppRepository    appRepository;
    private final INotifyInfoRepository   notifyRepository;
    private final NotifyPushDomainService domainService;

    public void schedule() {
        appRepository.ofAllKeys()
                     .stream()
                     .flatMap(appKey -> notifyRepository.notifyIdList(appKey).stream())
                     .forEach(notifyId -> executor.execute(() -> domainService.tagGroupPush(notifyId)));
    }

}
