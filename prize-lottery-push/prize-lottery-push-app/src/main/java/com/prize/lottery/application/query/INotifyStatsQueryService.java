package com.prize.lottery.application.query;


import com.prize.lottery.application.query.dto.NotifyChartQuery;
import com.prize.lottery.application.query.vo.CensusLineChartVo;
import com.prize.lottery.infrast.persist.vo.DeviceMetricsVo;
import com.prize.lottery.infrast.persist.vo.PushMetricsVo;

public interface INotifyStatsQueryService {

    CensusLineChartVo<Long> deviceMetrics(NotifyChartQuery query);

    CensusLineChartVo<Long> notifyMetrics(NotifyChartQuery query);

    PushMetricsVo getAppPushMetrics(Long appKey);

    DeviceMetricsVo getAppDeviceMetrics(Long appKey);

}
