package com.prize.lottery.application.query.impl;

import com.prize.lottery.application.query.INotifyStatsQueryService;
import com.prize.lottery.application.query.dto.NotifyChartQuery;
import com.prize.lottery.application.query.executor.NotifyDeviceChartQueryExecutor;
import com.prize.lottery.application.query.executor.NotifyPushChartQueryExecutor;
import com.prize.lottery.application.query.vo.CensusLineChartVo;
import com.prize.lottery.infrast.persist.mapper.NotifyAppMapper;
import com.prize.lottery.infrast.persist.mapper.NotifyInfoMapper;
import com.prize.lottery.infrast.persist.vo.DeviceMetricsVo;
import com.prize.lottery.infrast.persist.vo.PushMetricsVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotifyStatsQueryService implements INotifyStatsQueryService {

    private final NotifyAppMapper                notifyAppMapper;
    private final NotifyInfoMapper               notifyInfoMapper;
    private final NotifyPushChartQueryExecutor   pushChartQueryExecutor;
    private final NotifyDeviceChartQueryExecutor deviceChartQueryExecutor;

    @Override
    public CensusLineChartVo<Long> deviceMetrics(NotifyChartQuery query) {
        return deviceChartQueryExecutor.execute(query);
    }

    @Override
    public CensusLineChartVo<Long> notifyMetrics(NotifyChartQuery query) {
        return pushChartQueryExecutor.execute(query);
    }

    @Override
    public PushMetricsVo getAppPushMetrics(Long appKey) {
        return notifyInfoMapper.getPushMetricsVo(appKey);
    }

    @Override
    public DeviceMetricsVo getAppDeviceMetrics(Long appKey) {
        DeviceMetricsVo metrics    = notifyAppMapper.getDeviceMetricsVo(appKey);
        DeviceMetricsVo cntMetrics = notifyAppMapper.getDeviceMetricsCnt(appKey);
        metrics.setLastCnt(cntMetrics.getLastCnt());
        metrics.setYesterdayCnt(cntMetrics.getYesterdayCnt());
        return metrics;
    }

}
