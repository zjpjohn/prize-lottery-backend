package com.prize.lottery.application.query.executor;

import com.google.common.collect.Maps;
import com.prize.lottery.application.query.dto.NotifyChartQuery;
import com.prize.lottery.application.query.vo.CensusLineChartVo;
import com.prize.lottery.infrast.persist.mapper.NotifyAppMapper;
import com.prize.lottery.infrast.persist.po.MetricsDevicePo;
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
public class NotifyDeviceChartQueryExecutor {

    private final NotifyAppMapper mapper;

    public CensusLineChartVo<Long> execute(NotifyChartQuery query) {
        LocalDate             endDay       = LocalDate.now();
        LocalDate             startDay     = endDay.minusDays(query.getDays());
        List<MetricsDevicePo> devices      = mapper.getMetricsDevices(query.getAppKey(), startDay, endDay);
        Long[]                newDevices   = new Long[query.getDays()];
        Long[]                totalDevices = new Long[query.getDays()];
        Map<LocalDate, MetricsDevicePo> map = devices.stream()
                                                     .collect(Collectors.toMap(MetricsDevicePo::getMetricsDate, v -> v));
        List<String> xAxis = IntStream.range(0, query.getDays()).mapToObj(i -> {
            newDevices[i] = totalDevices[i] = 0L;
            LocalDate       dayIndex = startDay.plusDays(i);
            MetricsDevicePo device   = map.get(dayIndex);
            if (device != null) {
                newDevices[i]   = device.getIncreases();
                totalDevices[i] = device.getDevices();
            }
            return dayIndex.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
        }).collect(Collectors.toList());
        Map<String, Long[]> series = Maps.newHashMap();
        series.put("new", newDevices);
        series.put("total", totalDevices);
        return new CensusLineChartVo<>(xAxis, series);
    }

}
