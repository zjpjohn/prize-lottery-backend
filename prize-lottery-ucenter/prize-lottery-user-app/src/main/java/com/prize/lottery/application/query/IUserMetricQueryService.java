package com.prize.lottery.application.query;


import com.prize.lottery.application.query.vo.CensusLineChartVo;
import com.prize.lottery.application.query.vo.UserMetricsTrendVo;
import com.prize.lottery.application.query.vo.UserTotalMetricsVo;
import com.prize.lottery.infrast.persist.vo.MemberTotalMetricsVo;
import com.prize.lottery.infrast.persist.vo.UserMetricsVo;

import java.util.List;

public interface IUserMetricQueryService {

    UserMetricsVo userMetrics();

    List<UserMetricsTrendVo> metricsTrendList();

    CensusLineChartVo<Integer> metricsChart(Integer day);

    UserTotalMetricsVo userTotalMetrics(Integer days);

    MemberTotalMetricsVo memberTotalMetrics(Integer days);

}
