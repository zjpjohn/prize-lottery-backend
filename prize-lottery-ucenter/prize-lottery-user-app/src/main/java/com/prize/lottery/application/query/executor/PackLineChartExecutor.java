package com.prize.lottery.application.query.executor;

import com.google.common.collect.Maps;
import com.prize.lottery.application.query.vo.CensusLineChartVo;
import com.prize.lottery.infrast.persist.mapper.PackInfoMapper;
import com.prize.lottery.infrast.persist.vo.PackDayMetricsVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class PackLineChartExecutor {

    private static final String PAY_AMT = "payAmt";
    private static final String PAY_CNT = "payCnt";

    private final PackInfoMapper mapper;

    public CensusLineChartVo<Long> execute(Integer day) {
        LocalDateTime      endDay   = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime      startDay = endDay.minusDays(day - 1);
        final List<String> xAxis    = this.xAxis(startDay.toLocalDate(), day);
        final Long[]       amt      = new Long[xAxis.size()];
        final Long[]       cnt      = new Long[xAxis.size()];
        for (int i = 0; i < amt.length; i++) {
            amt[i] = cnt[i] = 0L;
        }
        List<PackDayMetricsVo>           metrics    = mapper.getPackDayMetrics(startDay, endDay);
        Map<LocalDate, PackDayMetricsVo> metricsMap = Maps.uniqueIndex(metrics, PackDayMetricsVo::getDay);
        for (Map.Entry<LocalDate, PackDayMetricsVo> entry : metricsMap.entrySet()) {
            String           key   = this.format(entry.getKey());
            int              index = xAxis.indexOf(key);
            PackDayMetricsVo value = entry.getValue();
            amt[index] = value.getPayAmt();
            cnt[index] = value.getPayCnt().longValue();
        }
        Map<String, Long[]> series = Maps.newHashMap();
        series.put(PAY_AMT, amt);
        series.put(PAY_CNT, cnt);
        return new CensusLineChartVo<>(xAxis, series);
    }


    public List<String> xAxis(LocalDate start, int size) {
        return IntStream.range(0, size).mapToObj(i -> format(start.plusDays(i))).collect(Collectors.toList());
    }

    public String format(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MM/dd"));
    }

}
