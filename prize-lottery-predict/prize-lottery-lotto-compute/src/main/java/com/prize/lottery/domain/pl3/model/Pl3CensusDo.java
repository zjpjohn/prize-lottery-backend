package com.prize.lottery.domain.pl3.model;


import com.prize.lottery.enums.*;
import com.prize.lottery.utils.ICaiConstants;
import com.prize.lottery.value.CensusValue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Pl3CensusDo {

    /**
     * 统计期号
     */
    private String      period;
    /**
     * 统计数据数量级别
     */
    private Integer     level;
    /**
     * 统计数据字段渠道
     */
    private Pl3Channel  channel;
    /**
     * 统计图表类型
     */
    private ChartType   chartType;
    /**
     * 统计数据
     */
    private CensusValue census;

    public Pl3CensusDo create(String period, Pl3Channel channel, ChartType type) {
        return create(period, null, channel, type);
    }

    public Pl3CensusDo create(String period, Integer level, Pl3Channel channel, ChartType chartType) {
        this.period    = period;
        this.level     = level;
        this.channel   = channel;
        this.chartType = chartType;
        return this;
    }

    public Pl3CensusDo calcCensus(List<String> data) {
        return calcCensus(data, FullChartEnums.LEVEL_1000);
    }

    public Pl3CensusDo calcCensus(List<String> data, ChartCalculator calculator) {
        if (channel.chartValue() == ChartValue.PLAIN) {
            this.census = calculator.calcPlain(data, ICaiConstants.PL3_BALLS);
            return this;
        }
        this.census = calculator.calcDuplex(data, ICaiConstants.FC3D_BALLS);
        return this;
    }

    public static List<Pl3CensusDo> calcItem(String period, Pl3Channel channel, List<String> data) {
        return Arrays.stream(ItemChartEnums.values())
                     .map(v -> new Pl3CensusDo().create(period, v.value(), channel, ChartType.ITEM_CHART)
                                                .calcCensus(data, v))
                     .collect(Collectors.toList());
    }

    public static List<Pl3CensusDo> calcFull(String period, Pl3Channel channel, List<String> data) {
        return Arrays.stream(FullChartEnums.values())
                     .map(v -> new Pl3CensusDo().create(period, v.value(), channel, ChartType.ALL_CHART)
                                                .calcCensus(data, v))
                     .collect(Collectors.toList());
    }

    public static List<Pl3CensusDo> calcVip(String period, Pl3Channel channel, List<String> data) {
        return Arrays.stream(VipChartEnums.values())
                     .map(v -> new Pl3CensusDo().create(period, v.value(), channel, ChartType.VIP_CHART)
                                                .calcCensus(data, v))
                     .collect(Collectors.toList());
    }

    public String getPeriod() {
        return period;
    }

    public Integer getLevel() {
        return level;
    }

    public Pl3Channel getChannel() {
        return channel;
    }

    public ChartType getChartType() {
        return chartType;
    }

    public CensusValue getCensus() {
        return census;
    }
}
