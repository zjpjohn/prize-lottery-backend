package com.prize.lottery.application.schedule;

import com.prize.lottery.application.command.executor.TagGroupBindExecutor;
import com.prize.lottery.infrast.persist.enums.CommonState;
import com.prize.lottery.infrast.persist.mapper.NotifyAppMapper;
import com.prize.lottery.infrast.persist.mapper.NotifyTagMapper;
import com.prize.lottery.infrast.persist.po.NotifyAppPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.prize.lottery.infrast.config.ExecutorConfigurer.PUSH_EXECUTOR;


@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceTagBindScheduler {

    @Qualifier(PUSH_EXECUTOR)
    private final ThreadPoolTaskExecutor executor;
    private final NotifyAppMapper        appMapper;
    private final NotifyTagMapper        tagMapper;
    private final TagGroupBindExecutor   bindExecutor;

    public void schedule() {
        List<NotifyAppPo> appList = appMapper.getNotifyAppList();
        appList.stream()
               .flatMap(app -> tagMapper.getTagGroupList(app.getAppKey(), CommonState.USING.value()).stream())
               .forEach(group -> executor.execute(() -> bindExecutor.execute(group.getAppKey(), group.getId())));

    }

}
