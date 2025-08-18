package com.prize.lottery.application.schedule;

import com.prize.lottery.application.command.executor.DeviceMetricSyncExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceMetricsScheduler {

    private final DeviceMetricSyncExecutor deviceMetricSyncExecutor;

    public void schedule() {
        deviceMetricSyncExecutor.execute();
    }

}
