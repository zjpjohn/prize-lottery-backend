package com.prize.lottery.domain.ssq.model;


import com.prize.lottery.enums.*;
import com.prize.lottery.value.CensusValue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SsqCensusDo {

    /**
     * 数据统计期号
     */
    private String      period;
    /**
     * 统计数据数量级别
     */
    private Integer     level;
    /**
     * 双色球统计数据字段渠道
     */
    private SsqChannel  channel;
    /**
     * 统计图标类型
     */
    private ChartType   chartType;
    /**
     * 统计数据
     */
    private CensusValue census;

    public SsqCensusDo create(String period, SsqChannel channel, ChartType type) {
        return create(period, null, channel, type);
    }

    public SsqCensusDo create(String period, Integer level, SsqChannel channel, ChartType chartType) {
        this.period    = period;
        this.level     = level;
        this.channel   = channel;
        this.chartType = chartType;
        return this;
    }

    public SsqCensusDo calcCensus(List<String> data) {
        return this.calcCensus(data, FullChartEnums.LEVEL_1000);
    }

    public SsqCensusDo calcCensus(List<String> data, ChartCalculator calculator) {
        this.census = calculator.calcPlain(data, channel.keys());
        return this;
    }

    public static List<SsqCensusDo> calcItem(String period, SsqChannel channel, List<String> data) {
        return Arrays.stream(ItemChartEnums.values())
                     .map(v -> new SsqCensusDo().create(period, v.value(), channel, ChartType.ITEM_CHART)
                                                .calcCensus(data, v))
                     .collect(Collectors.toList());
    }

    public static List<SsqCensusDo> calcFull(String period, SsqChannel channel, List<String> data) {
        return Arrays.stream(FullChartEnums.values())
                     .map(v -> new SsqCensusDo().create(period, v.value(), channel, ChartType.ALL_CHART)
                                                .calcCensus(data, v))
                     .collect(Collectors.toList());
    }

    public static List<SsqCensusDo> calcVip(String period, SsqChannel channel, List<String> data) {
        return Arrays.stream(VipChartEnums.values())
                     .map(v -> new SsqCensusDo().create(period, v.value(), channel, ChartType.VIP_CHART)
                                                .calcCensus(data, v))
                     .collect(Collectors.toList());
    }

    public String getPeriod() {
        return period;
    }

    public Integer getLevel() {
        return level;
    }

    public SsqChannel getChannel() {
        return channel;
    }

    public ChartType getChartType() {
        return chartType;
    }

    public CensusValue getCensus() {
        return census;
    }
}
