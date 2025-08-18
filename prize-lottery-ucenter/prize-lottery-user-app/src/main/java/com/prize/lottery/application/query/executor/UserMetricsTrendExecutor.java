package com.prize.lottery.application.query.executor;

import com.google.common.collect.Lists;
import com.prize.lottery.application.query.vo.UserMetricsTrendVo;
import com.prize.lottery.infrast.persist.mapper.UserStatisticsMapper;
import com.prize.lottery.infrast.persist.po.UserStatisticsPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserMetricsTrendExecutor {

    private final UserStatisticsMapper statisticsMapper;

    /**
     * 最近一周用户统计趋势指标
     */
    public List<UserMetricsTrendVo> execute() {
        List<UserStatisticsPo> metricsList = statisticsMapper.getLatestMetricsList(8);
        if (CollectionUtils.isEmpty(metricsList)) {
            return Collections.emptyList();
        }
        int                      size   = metricsList.size();
        int                      start  = size <= 7 ? size - 1 : size - 2;
        UserStatisticsPo         last   = metricsList.get(size <= 7 ? start : start + 1);
        List<UserMetricsTrendVo> trends = Lists.newArrayList();
        for (int i = start; i >= 0; i--) {
            UserStatisticsPo   current      = metricsList.get(i);
            UserMetricsTrendVo metricsTrend = calcMetricsTrend(current, last);
            trends.add(metricsTrend);
            last = current;
        }
        //倒序排列
        Collections.reverse(trends);
        return trends;
    }

    private UserMetricsTrendVo calcMetricsTrend(UserStatisticsPo current, UserStatisticsPo last) {
        UserMetricsTrendVo trend = new UserMetricsTrendVo();
        trend.setDay(current.getDay());
        trend.setIncr(calcTrend(current.getRegister(), last.getRegister()));
        trend.setActive(calcTrend(current.getActive(), last.getActive()));
        trend.setInvite(calcTrend(current.getInvite(), last.getInvite()));
        trend.setLaunch(calcTrend(current.getLaunch(), last.getLaunch()));
        trend.setLaunchAvg(calcTrend(current.getLaunchAvg(), last.getLaunchAvg()));
        return trend;
    }

    private UserMetricsTrendVo.TrendValue calcTrend(Integer value, Integer last) {
        Integer trend = Optional.ofNullable(last).map(e -> Integer.compare(value - e, 0)).orElse(0);
        return new UserMetricsTrendVo.TrendValue(value, trend);
    }

}
