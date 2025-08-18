package com.prize.lottery.domain.kl8.model;

import com.prize.lottery.enums.ChartCalculator;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.FullChartEnums;
import com.prize.lottery.enums.Kl8Channel;
import com.prize.lottery.utils.ICaiConstants;
import com.prize.lottery.value.CensusValue;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Kl8CensusDo {

    /**
     * 统计数据期号
     */
    private String      period;
    /**
     * 统计数量级别
     */
    private Integer     level;
    /**
     * 统计数据字段渠道
     */
    private Kl8Channel  channel;
    /**
     * 图标类型
     */
    private ChartType   chartType;
    /**
     * 统计数据
     */
    private CensusValue census;

    public Kl8CensusDo(String period, Kl8Channel channel, ChartType type) {
        this(period, null, channel, type);
    }

    public Kl8CensusDo(String period, Integer level, Kl8Channel channel, ChartType type) {
        this.period    = period;
        this.level     = level;
        this.channel   = channel;
        this.chartType = type;
    }

    /**
     * 计算前1000名专家预测数据统计
     *
     * @param data 预测数据
     */
    public Kl8CensusDo calcCensus(List<String> data) {
        return calcCensus(data, FullChartEnums.LEVEL_1000);
    }

    /**
     * 指定数量的预测数据计算统计
     *
     * @param data       预测数据
     * @param calculator 统计计算器
     */
    public Kl8CensusDo calcCensus(List<String> data, ChartCalculator calculator) {
        this.census = calculator.calcPlain(data, ICaiConstants.KL8_BALLS);
        return this;
    }

    /**
     * 全量增量统计
     *
     * @param period  统计期号
     * @param channel 数据字段渠道
     * @param data    统计数据
     */
    public static List<Kl8CensusDo> calcFull(String period, Kl8Channel channel, List<String> data) {
        return Arrays.stream(FullChartEnums.values()).map(v -> {
            Kl8CensusDo censusDo = new Kl8CensusDo(period, v.getLimit(), channel, ChartType.ALL_CHART);
            return censusDo.calcCensus(data);
        }).collect(Collectors.toList());
    }

}
