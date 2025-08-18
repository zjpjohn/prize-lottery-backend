package com.prize.lottery.application.query.executor;

import com.google.common.collect.Maps;
import com.prize.lottery.application.query.dto.NotifyChartQuery;
import com.prize.lottery.application.query.vo.CensusLineChartVo;
import com.prize.lottery.infrast.persist.mapper.NotifyInfoMapper;
import com.prize.lottery.infrast.persist.po.MetricsNotifyPo;
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
public class NotifyPushChartQueryExecutor {

    private final NotifyInfoMapper mapper;

    public CensusLineChartVo<Long> execute(NotifyChartQuery query) {
        LocalDate             endDay   = LocalDate.now();
        LocalDate             startDay = endDay.minusDays(query.getDays());
        Long[]                sent     = new Long[query.getDays()], accept = new Long[query.getDays()], opened = new Long[query.getDays()], deleted = new Long[query.getDays()], receive = new Long[query.getDays()];
        List<MetricsNotifyPo> list     = mapper.getMetricsNotifyList(query.getAppKey(), startDay, endDay);
        Map<LocalDate, MetricsNotifyPo> map = list.stream()
                                                  .collect(Collectors.toMap(MetricsNotifyPo::getMetricsDate, v -> v));
        List<String> xAxis = IntStream.range(0, query.getDays()).mapToObj(i -> {
            sent[i] = accept[i] = opened[i] = deleted[i] = receive[i] = 0L;
            LocalDate       dayIndex = startDay.plusDays(i);
            MetricsNotifyPo metrics  = map.get(dayIndex);
            if (metrics != null) {
                sent[i]    = metrics.getSent();
                accept[i]  = metrics.getAccept();
                opened[i]  = metrics.getOpened();
                deleted[i] = metrics.getDeleted();
                receive[i] = metrics.getReceive();
            }
            return dayIndex.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
        }).collect(Collectors.toList());
        Map<String, Long[]> series = Maps.newHashMap();
        series.put("sent", sent);
        series.put("accept", accept);
        series.put("opened", opened);
        series.put("deleted", deleted);
        series.put("receive", receive);
        return new CensusLineChartVo<>(xAxis, series);
    }

}
