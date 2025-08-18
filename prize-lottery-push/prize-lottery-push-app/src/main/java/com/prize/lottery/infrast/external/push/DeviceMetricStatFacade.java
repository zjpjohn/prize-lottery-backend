package com.prize.lottery.infrast.external.push;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.push.model.v20160801.QueryDeviceStatRequest;
import com.aliyuncs.push.model.v20160801.QueryDeviceStatResponse;
import com.prize.lottery.infrast.external.push.request.DeviceMetricDto;
import com.prize.lottery.infrast.external.push.response.DeviceMetricStat;
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
public class DeviceMetricStatFacade {

    private final IAcsClient client;

    /**
     * 查询设备留存统计
     */
    public List<DeviceMetricStat> execute(DeviceMetricDto data) {
        try {
            QueryDeviceStatRequest request = new QueryDeviceStatRequest();
            request.setAppKey(data.appKey());
            request.setDeviceType("ANDROID");
            request.setStartTime(DateUtils.formatUtc(data.startTime()));
            request.setEndTime(DateUtils.formatUtc(data.endTime()));
            request.setQueryType(data.queryType());
            QueryDeviceStatResponse                     response = client.getAcsResponse(request);
            List<QueryDeviceStatResponse.AppDeviceStat> stats    = response.getAppDeviceStats();
            if (CollectionUtils.isEmpty(stats)) {
                return Collections.emptyList();
            }
            return stats.stream().map(s -> {
                DeviceMetricStat stat = new DeviceMetricStat();
                stat.setCount(s.getCount());
                stat.setDeviceType(s.getDeviceType());
                stat.setQueryType(data.queryType());
                stat.setTime(DateUtils.parseUtc(s.getTime()));
                return stat;
            }).collect(Collectors.toList());
        } catch (Exception error) {
            log.error(error.getMessage(), error);
        }
        return Collections.emptyList();

    }
}
