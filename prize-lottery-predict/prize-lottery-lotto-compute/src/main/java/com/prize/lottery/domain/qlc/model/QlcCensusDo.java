package com.prize.lottery.domain.qlc.model;


import com.prize.lottery.enums.*;
import com.prize.lottery.utils.ICaiConstants;
import com.prize.lottery.value.CensusValue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QlcCensusDo {

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
    private QlcChannel  channel;
    /**
     * 统计图表类型
     */
    private ChartType   chartType;
    /**
     * 统计数据
     */
    private CensusValue census;

    public QlcCensusDo create(String period, QlcChannel channel, ChartType type) {
        return this.create(period, null, channel, type);
    }

    public QlcCensusDo create(String period, Integer level, QlcChannel channel, ChartType type) {
        this.period    = period;
        this.level     = level;
        this.channel   = channel;
        this.chartType = type;
        return this;
    }

    public QlcCensusDo calcCensus(List<String> data) {
        return calcCensus(data, FullChartEnums.LEVEL_1000);
    }

    public QlcCensusDo calcCensus(List<String> data, ChartCalculator calculator) {
        this.census = calculator.calcPlain(data, ICaiConstants.QLC_BALLS);
        return this;
    }

    public static List<QlcCensusDo> calcItem(String period, QlcChannel channel, List<String> data) {
        return Arrays.stream(ItemChartEnums.values())
                     .map(v -> new QlcCensusDo().create(period, v.value(), channel, ChartType.ITEM_CHART)
                                                .calcCensus(data, v))
                     .collect(Collectors.toList());
    }

    public static List<QlcCensusDo> calcFull(String period, QlcChannel channel, List<String> data) {
        return Arrays.stream(FullChartEnums.values())
                     .map(v -> new QlcCensusDo().create(period, v.value(), channel, ChartType.ALL_CHART)
                                                .calcCensus(data, v))
                     .collect(Collectors.toList());
    }

    public static List<QlcCensusDo> calcVip(String period, QlcChannel channel, List<String> data) {
        return Arrays.stream(VipChartEnums.values())
                     .map(v -> new QlcCensusDo().create(period, v.value(), channel, ChartType.VIP_CHART)
                                                .calcCensus(data, v))
                     .collect(Collectors.toList());
    }

    public String getPeriod() {
        return period;
    }

    public Integer getLevel() {
        return level;
    }

    public QlcChannel getChannel() {
        return channel;
    }

    public ChartType getChartType() {
        return chartType;
    }

    public CensusValue getCensus() {
        return census;
    }
}
