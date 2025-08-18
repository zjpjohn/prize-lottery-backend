package com.prize.lottery.application.query.executor;

import com.google.common.collect.Maps;
import com.prize.lottery.application.query.vo.CensusLineChartVo;
import com.prize.lottery.infrast.persist.mapper.OrderInfoMapper;
import com.prize.lottery.infrast.persist.po.OrderStatisticsPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderLineChartExecutor {

    private static final String SERIES_SUCCESS = "success";
    private static final String SERIES_FAILURE = "failure";
    private static final String SERIES_CLOSED  = "closed";

    private final OrderInfoMapper orderMapper;

    public CensusLineChartVo execute(Integer day) {
        LocalDate                         endDay     = LocalDate.now();
        LocalDate                         startDay   = endDay.minusDays(day - 1);
        final List<String>                xAxis      = this.xAxis(startDay, day);
        final long[]                      success    = new long[xAxis.size()];
        final long[]                      failure    = new long[xAxis.size()];
        final long[]                      closed     = new long[xAxis.size()];
        List<OrderStatisticsPo>           statistics = orderMapper.getStatisticsList(startDay, endDay);
        Map<LocalDate, OrderStatisticsPo> metricsMap = Maps.uniqueIndex(statistics, OrderStatisticsPo::getDay);
        for (Map.Entry<LocalDate, OrderStatisticsPo> entry : metricsMap.entrySet()) {
            String            key   = this.format(entry.getKey());
            int               index = xAxis.indexOf(key);
            OrderStatisticsPo value = entry.getValue();
            success[index] = value.getSuccessAmt();
            failure[index] = value.getFailureAmt();
            closed[index]  = value.getClosedAmt();
        }
        Map<String, long[]> series = Maps.newHashMap();
        series.put(SERIES_SUCCESS, success);
        series.put(SERIES_FAILURE, failure);
        series.put(SERIES_CLOSED, closed);
        return new CensusLineChartVo(xAxis, series);
    }

    public List<String> xAxis(LocalDate start, int size) {
        return IntStream.range(0, size).mapToObj(i -> format(start.plusDays(i))).collect(Collectors.toList());
    }

    public String format(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MM/dd"));
    }

}
