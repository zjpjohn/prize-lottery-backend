package com.prize.lottery.domain.fc3d.model;


import com.prize.lottery.enums.*;
import com.prize.lottery.utils.ICaiConstants;
import com.prize.lottery.value.CensusValue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Fc3dCensusDo {

    /**
     * 数据统计期号
     */
    private String      period;
    /**
     * 统计数量级别
     */
    private Integer     level;
    /**
     * 统计数据字段渠道
     */
    private Fc3dChannel channel;
    /**
     * 统计图表类型
     */
    private ChartType   chartType;
    /**
     * 统计数据
     */
    private CensusValue census;

    public Fc3dCensusDo create(String period, Fc3dChannel channel, ChartType type) {
        return create(period, null, channel, type);
    }

    public Fc3dCensusDo create(String period, Integer level, Fc3dChannel channel, ChartType chartType) {
        this.period    = period;
        this.level     = level;
        this.channel   = channel;
        this.chartType = chartType;
        return this;
    }

    public Fc3dCensusDo calcCensus(List<String> data) {
        return calcCensus(data, FullChartEnums.LEVEL_1000);
    }

    private Fc3dCensusDo calcCensus(List<String> data, ChartCalculator calculator) {
        if (channel.chartValue() == ChartValue.PLAIN) {
            this.census = calculator.calcPlain(data, ICaiConstants.FC3D_BALLS);
            return this;
        }
        this.census = calculator.calcDuplex(data, ICaiConstants.FC3D_BALLS);
        return this;
    }

    public static List<Fc3dCensusDo> calcItem(String period, Fc3dChannel channel, List<String> data) {
        return Arrays.stream(ItemChartEnums.values())
                     .map(v -> new Fc3dCensusDo().create(period, v.value(), channel, ChartType.ITEM_CHART)
                                                 .calcCensus(data, v))
                     .collect(Collectors.toList());
    }

    public static List<Fc3dCensusDo> calcFull(String period, Fc3dChannel channel, List<String> data) {
        return Arrays.stream(FullChartEnums.values())
                     .map(v -> new Fc3dCensusDo().create(period, v.value(), channel, ChartType.ALL_CHART)
                                                 .calcCensus(data, v))
                     .collect(Collectors.toList());
    }

    public static List<Fc3dCensusDo> calcVip(String period, Fc3dChannel channel, List<String> data) {
        return Arrays.stream(VipChartEnums.values())
                     .map(v -> new Fc3dCensusDo().create(period, v.value(), channel, ChartType.VIP_CHART)
                                                 .calcCensus(data, v))
                     .collect(Collectors.toList());
    }

    public String getPeriod() {
        return period;
    }

    public Integer getLevel() {
        return level;
    }

    public Fc3dChannel getChannel() {
        return channel;
    }

    public ChartType getChartType() {
        return chartType;
    }

    public CensusValue getCensus() {
        return census;
    }
}
