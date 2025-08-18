package com.prize.lottery.application.command.executor;

import com.google.common.collect.Lists;
import com.prize.lottery.infrast.external.push.DeviceMetricStatFacade;
import com.prize.lottery.infrast.external.push.request.DeviceMetricDto;
import com.prize.lottery.infrast.external.push.response.DeviceMetricStat;
import com.prize.lottery.infrast.persist.mapper.NotifyAppMapper;
import com.prize.lottery.infrast.persist.po.MetricsDevicePo;
import com.prize.lottery.infrast.persist.po.NotifyAppPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceMetricSyncExecutor {

    private final NotifyAppMapper        notifyAppMapper;
    private final DeviceMetricStatFacade deviceStatFacade;

    @Transactional
    public void execute() {
        List<NotifyAppPo> appList   = notifyAppMapper.getNotifyAppList();
        LocalDateTime     endTime   = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime     startTime = endTime.minusDays(1);
        for (NotifyAppPo notifyApp : appList) {
            this.syncDeviceStat(notifyApp.getAppKey(), startTime, endTime);
        }
    }

    private void syncDeviceStat(Long appKey, LocalDateTime startTime, LocalDateTime endTime) {
        DeviceMetricDto        newMetricDto = DeviceMetricDto.newStat(appKey, startTime, endTime);
        List<DeviceMetricStat> stats        = Lists.newArrayList();
        stats.addAll(deviceStatFacade.execute(newMetricDto));
        DeviceMetricDto totalMetricDto = DeviceMetricDto.totalStat(appKey, startTime, endTime);
        stats.addAll(deviceStatFacade.execute(totalMetricDto));
        List<MetricsDevicePo> metricList = stats.stream().map(s -> {
            MetricsDevicePo po = new MetricsDevicePo();
            po.setAppKey(appKey);
            po.setLatestTime(LocalDateTime.now());
            po.setMetricsDate(s.getTime().toLocalDate());
            if (s.isNew()) {
                po.setIncreases(s.getCount());
            } else {
                po.setDevices(s.getCount());
            }
            return po;
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(metricList)) {
            notifyAppMapper.addDeviceMetrics(metricList);
        }
    }

}
