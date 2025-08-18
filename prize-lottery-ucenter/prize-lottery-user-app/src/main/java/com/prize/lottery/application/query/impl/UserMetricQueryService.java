package com.prize.lottery.application.query.impl;

import com.prize.lottery.application.query.IUserMetricQueryService;
import com.prize.lottery.application.query.executor.UserLineChartExecutor;
import com.prize.lottery.application.query.executor.UserMetricsTrendExecutor;
import com.prize.lottery.application.query.vo.CensusLineChartVo;
import com.prize.lottery.application.query.vo.UserMetricsTrendVo;
import com.prize.lottery.application.query.vo.UserTotalMetricsVo;
import com.prize.lottery.infrast.persist.mapper.UserInfoMapper;
import com.prize.lottery.infrast.persist.mapper.UserStatisticsMapper;
import com.prize.lottery.infrast.persist.vo.MemberTotalMetricsVo;
import com.prize.lottery.infrast.persist.vo.UserMetricsVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserMetricQueryService implements IUserMetricQueryService {

    private final UserLineChartExecutor    chartExecutor;
    private final UserMetricsTrendExecutor trendExecutor;
    private final UserInfoMapper           userInfoMapper;
    private final UserStatisticsMapper     statisticsMapper;

    @Override
    public UserMetricsVo userMetrics() {
        //用户统计信息
        UserMetricsVo metrics = statisticsMapper.getUserMetricsVo();
        //用户日活统计
        UserMetricsVo activeMetrics = statisticsMapper.getActiveMetricsVo();
        metrics.setYesterdayAct(activeMetrics.getYesterdayAct());
        metrics.setWeekAct(activeMetrics.getWeekAct());
        metrics.setMonthAct(activeMetrics.getMonthAct());
        metrics.setLastAct(activeMetrics.getLastAct());
        return metrics;
    }

    @Override
    public List<UserMetricsTrendVo> metricsTrendList() {
        return trendExecutor.execute();
    }

    @Override
    public CensusLineChartVo<Integer> metricsChart(Integer day) {
        return chartExecutor.execute(day);
    }

    @Override
    public UserTotalMetricsVo userTotalMetrics(Integer days) {
        LocalDate endDay   = LocalDate.now();
        LocalDate startDay = endDay.minusDays(days);
        return statisticsMapper.getUserTotalStatistics(startDay, endDay);
    }

    @Override
    public MemberTotalMetricsVo memberTotalMetrics(Integer days) {
        LocalDate endDay   = LocalDate.now();
        LocalDate startDay = endDay.minusDays(days);
        return userInfoMapper.getTotalMemberMetrics(startDay, endDay);
    }

}
