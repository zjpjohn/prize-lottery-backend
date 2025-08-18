package com.prize.lottery.application.query.executor;

import com.google.common.collect.Lists;
import com.prize.lottery.application.query.vo.OrderMetricsTrendVo;
import com.prize.lottery.infrast.persist.mapper.OrderInfoMapper;
import com.prize.lottery.infrast.persist.po.OrderStatisticsPo;
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
public class OrderMetricsTrendExecutor {

    private final OrderInfoMapper orderMapper;

    public List<OrderMetricsTrendVo> execute() {
        List<OrderStatisticsPo> metricsList = orderMapper.getLatestOrderMetrics(8);
        if (CollectionUtils.isEmpty(metricsList)) {
            return Collections.emptyList();
        }
        int                       size   = metricsList.size();
        int                       start  = size <= 7 ? size - 1 : size - 2;
        OrderStatisticsPo         last   = metricsList.get(size <= 7 ? start : start + 1);
        List<OrderMetricsTrendVo> trends = Lists.newArrayList();
        for (int i = start; i >= 0; i--) {
            OrderStatisticsPo current = metricsList.get(i);
            trends.add(calcMetricsTrend(current, last));
            last = current;
        }
        Collections.reverse(trends);
        return trends;
    }

    private OrderMetricsTrendVo calcMetricsTrend(OrderStatisticsPo current, OrderStatisticsPo last) {
        OrderMetricsTrendVo metricsTrend = new OrderMetricsTrendVo();
        metricsTrend.setDay(current.getDay());
        metricsTrend.setSuccessAmt(calcTrend(current.getSuccessAmt(), last.getSuccessAmt()));
        metricsTrend.setSuccessCnt(calcTrend(current.getSuccessCnt(), last.getSuccessCnt()));
        metricsTrend.setFailureAmt(calcTrend(current.getFailureAmt(), last.getFailureAmt()));
        metricsTrend.setFailureCnt(calcTrend(current.getFailureCnt(), last.getFailureCnt()));
        metricsTrend.setClosedAmt(calcTrend(current.getClosedAmt(), last.getClosedAmt()));
        metricsTrend.setClosedCnt(calcTrend(current.getClosedCnt(), last.getClosedCnt()));
        return metricsTrend;
    }

    private <T extends Comparable<T>> OrderMetricsTrendVo.TrendValue<T> calcTrend(T current, T last) {
        Integer trend = Optional.ofNullable(last).map(current::compareTo).orElse(0);
        return new OrderMetricsTrendVo.TrendValue<>(current, trend);
    }

}
