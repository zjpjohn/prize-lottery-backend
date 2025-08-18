package com.prize.lottery.domain.dlt.model;

import com.prize.lottery.enums.*;
import com.prize.lottery.value.CensusValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DltCensusDo {

    private String      period;
    private Integer     level;
    private DltChannel  channel;
    private ChartType   chartType;
    private CensusValue census;

    public DltCensusDo create(String period, DltChannel channel, ChartType type) {
        return this.create(period, null, channel, type);
    }

    public DltCensusDo create(String period, Integer level, DltChannel channel, ChartType type) {
        this.period    = period;
        this.level     = level;
        this.channel   = channel;
        this.chartType = type;
        return this;
    }

    public DltCensusDo calcCensus(List<String> data) {
        return calcCensus(data, FullChartEnums.LEVEL_1000);
    }

    public DltCensusDo calcCensus(List<String> data, ChartCalculator calculator) {
        this.census = calculator.calcPlain(data, this.channel.keys());
        return this;
    }

    public static List<DltCensusDo> calcItem(String period, DltChannel channel, List<String> data) {
        return Arrays.stream(ItemChartEnums.values())
                     .map(v -> new DltCensusDo().create(period, v.value(), channel, ChartType.ITEM_CHART)
                                                .calcCensus(data, v))
                     .collect(Collectors.toList());
    }

    public static List<DltCensusDo> calcFull(String period, DltChannel channel, List<String> data) {
        return Arrays.stream(FullChartEnums.values())
                     .map(v -> new DltCensusDo().create(period, v.value(), channel, ChartType.ALL_CHART)
                                                .calcCensus(data, v))
                     .collect(Collectors.toList());
    }

    public static List<DltCensusDo> calcVip(String period, DltChannel channel, List<String> data) {
        return Arrays.stream(VipChartEnums.values())
                     .map(v -> new DltCensusDo().create(period, v.value(), channel, ChartType.VIP_CHART)
                                                .calcCensus(data, v))
                     .collect(Collectors.toList());
    }

}
