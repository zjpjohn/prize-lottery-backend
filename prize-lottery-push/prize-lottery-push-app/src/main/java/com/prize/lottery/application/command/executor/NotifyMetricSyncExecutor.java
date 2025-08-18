package com.prize.lottery.application.command.executor;

import com.prize.lottery.infrast.external.push.NotifyMetricStatFacade;
import com.prize.lottery.infrast.external.push.request.NotifyMetricDto;
import com.prize.lottery.infrast.external.push.response.NotifyMetricStat;
import com.prize.lottery.infrast.persist.mapper.NotifyAppMapper;
import com.prize.lottery.infrast.persist.mapper.NotifyInfoMapper;
import com.prize.lottery.infrast.persist.po.MetricsNotifyPo;
import com.prize.lottery.infrast.persist.po.NotifyAppPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotifyMetricSyncExecutor {

    private final NotifyAppMapper        appMapper;
    private final NotifyInfoMapper       notifyMapper;
    private final NotifyMetricStatFacade statFacade;

    public void execute() {
        List<NotifyAppPo> appList   = appMapper.getNotifyAppList();
        LocalDateTime     endTime   = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime     startTime = endTime.minusDays(1);
        for (NotifyAppPo app : appList) {
            this.syncNotifyMetric(app.getAppKey(), startTime, endTime);
        }
    }


    private void syncNotifyMetric(Long appKey, LocalDateTime start, LocalDateTime end) {
        NotifyMetricDto        metricDto = new NotifyMetricDto(appKey, start, end);
        List<NotifyMetricStat> stats     = statFacade.execute(metricDto);
        List<MetricsNotifyPo> poList = stats.stream().map(s -> {
            MetricsNotifyPo po = new MetricsNotifyPo();
            po.setAppKey(appKey);
            po.setSent(s.getSentCount());
            po.setAccept(s.getAcceptCount());
            po.setOpened(s.getOpenedCount());
            po.setDeleted(s.getDeletedCount());
            po.setReceive(s.getReceivedCount());
            po.setLatestTime(LocalDateTime.now());
            po.setMetricsDate(s.getTime().toLocalDate());
            return po;
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(poList)) {
            notifyMapper.addNotifyMetrics(poList);
        }
    }

}
