package com.prize.lottery.infrast.external.push;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.push.model.v20160801.QueryPushStatByAppRequest;
import com.aliyuncs.push.model.v20160801.QueryPushStatByAppResponse;
import com.prize.lottery.infrast.external.push.request.NotifyMetricDto;
import com.prize.lottery.infrast.external.push.response.NotifyMetricStat;
import com.prize.lottery.infrast.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotifyMetricStatFacade {

    private final IAcsClient client;

    public List<NotifyMetricStat> execute(NotifyMetricDto data) {
        try {
            QueryPushStatByAppRequest request = new QueryPushStatByAppRequest();
            request.setAppKey(data.appKey());
            request.setGranularity("DAY");
            request.setStartTime(DateUtils.formatUtc(data.startTime()));
            request.setEndTime(DateUtils.formatUtc(data.endTime()));
            QueryPushStatByAppResponse                   response = client.getAcsResponse(request);
            List<QueryPushStatByAppResponse.AppPushStat> stats    = response.getAppPushStats();
            if (CollectionUtils.isEmpty(stats)) {
                return Collections.emptyList();
            }
            return stats.stream().map(s -> {
                NotifyMetricStat stat = new NotifyMetricStat();
                stat.setSentCount(s.getSentCount());
                stat.setAcceptCount(s.getAcceptCount());
                stat.setOpenedCount(s.getOpenedCount());
                stat.setDeletedCount(s.getDeletedCount());
                stat.setReceivedCount(s.getReceivedCount());
                stat.setTime(DateUtils.parseUtc(s.getTime()));
                return stat;
            }).collect(Collectors.toList());
        } catch (Exception error) {
            log.error(error.getMessage(), error);
        }
        return Collections.emptyList();
    }
}
