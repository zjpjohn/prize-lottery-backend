package com.prize.lottery.application.query.executor;

import com.google.common.collect.Maps;
import com.prize.lottery.application.query.vo.CensusLineChartVo;
import com.prize.lottery.infrast.persist.mapper.UserStatisticsMapper;
import com.prize.lottery.infrast.persist.po.UserStatisticsPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserLineChartExecutor {

    public static final String SERIES_ADDED      = "added";
    public static final String SERIES_ACTIVE     = "active";
    public static final String SERIES_LAUNCH     = "launch";
    public static final String SERIES_LAUNCH_AVG = "launchAvg";
    public static final String SERIES_INVITE     = "invite";

    private final UserStatisticsMapper statisticsMapper;

    public CensusLineChartVo<Integer> execute(Integer day) {
        LocalDate          endDay    = LocalDate.now().minusDays(1);
        LocalDate          startDay  = endDay.minusDays(day - 1);
        final List<String> xAxis     = this.xAxis(startDay, day);
        final Integer[]    added     = new Integer[xAxis.size()];
        final Integer[]    active    = new Integer[xAxis.size()];
        final Integer[]    launch    = new Integer[xAxis.size()];
        final Integer[]    launchAvg = new Integer[xAxis.size()];
        final Integer[]    invite    = new Integer[xAxis.size()];
        for (int i = 0; i < added.length; i++) {
            added[i]     = 0;
            active[i]    = 0;
            launch[i]    = 0;
            launchAvg[i] = 0;
            invite[i]    = 0;
        }
        final List<UserStatisticsPo> statistics = statisticsMapper.getStatisticsList(startDay, endDay);
        Map<LocalDate, UserStatisticsPo> collect = statistics.stream()
                                                             .collect(Collectors.toMap(UserStatisticsPo::getDay, Function.identity()));
        for (Map.Entry<LocalDate, UserStatisticsPo> entry : collect.entrySet()) {
            String           key   = format(entry.getKey());
            int              index = xAxis.indexOf(key);
            UserStatisticsPo value = entry.getValue();
            added[index]     = value.getRegister();
            active[index]    = value.getActive();
            launch[index]    = value.getLaunch();
            launchAvg[index] = value.getLaunchAvg();
            invite[index]    = value.getInvite();
        }
        Map<String, Integer[]> series = Maps.newHashMap();
        series.put(SERIES_ADDED, added);
        series.put(SERIES_ACTIVE, active);
        series.put(SERIES_INVITE, invite);
        series.put(SERIES_LAUNCH, launch);
        series.put(SERIES_LAUNCH_AVG, launchAvg);
        return new CensusLineChartVo<>(xAxis, series);
    }

    public List<String> xAxis(LocalDate start, int size) {
        return IntStream.range(0, size).mapToObj(i -> format(start.plusDays(i))).collect(Collectors.toList());
    }

    public String format(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MM/dd"));
    }

}
