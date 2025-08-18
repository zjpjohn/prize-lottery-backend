package com.prize.lottery.domain.value;

import com.cloud.arch.utils.CollectionUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.prize.lottery.enums.ItemChartEnums;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.value.CensusValue;
import jodd.util.MapEntry;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Data
public class CensusOperator {

    //前10名统计
    private CensusValue level10;
    //前20名统计
    private CensusValue level20;
    //前50名统计
    private CensusValue level50;
    //前100名统计
    private CensusValue level100;
    //前150名统计
    private CensusValue level150;

    private Map<Long, List<String>> convert(CensusValue census) {
        List<Long>              values = census.getValues().get(0);
        Map<Long, List<String>> result = Maps.newHashMap();
        for (int i = 0; i < values.size(); i++) {
            String       key   = String.valueOf(i);
            Long         value = values.get(i);
            List<String> sets  = result.get(value);
            if (CollectionUtils.isNotEmpty(sets)) {
                sets.add(key);
                continue;
            }
            sets = Lists.newArrayList(key);
            result.put(value, sets);
        }
        return result;
    }

    public Map<String, Double> getLevel10Weight() {
        return calcWeight(new TreeMap<>(this.convert(level10)));
    }

    public Map<String, Double> getLevel20Weight() {
        return calcWeight(new TreeMap<>(this.convert(level20)));
    }

    public Map<String, Double> getLevel50Weight() {
        return calcWeight(new TreeMap<>(this.convert(level50)));
    }

    public Map<String, Double> getLevel100Weight() {
        return calcWeight(new TreeMap<>(this.convert(level100)));
    }

    public Map<String, Double> getLevel150Weight() {
        return calcWeight(new TreeMap<>(this.convert(level150)));
    }

    private Map<String, Double> calcWeight(TreeMap<Long, List<String>> census) {
        Long maxValue = census.lastKey();
        return census.entrySet().stream().flatMap(entry -> {
            Double weight = scaleValue(entry.getKey() * 1.0 / maxValue);
            return entry.getValue().stream().map(v -> new MapEntry<String, Double>(v, weight));
        }).collect(Collectors.toMap(MapEntry::getKey, MapEntry::getValue));
    }

    public double scaleValue(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 基于字段统计级别构建计算算子
     *
     * @param list 字段统计数据
     */
    public static <T extends BaseLottoCensus> CensusOperator fromChannelCensus(List<T> list) {
        Map<Integer, T> censusMap = CollectionUtils.toMap(list, BaseLottoCensus::getLevel);
        CensusOperator  operator  = new CensusOperator();
        Optional.ofNullable(censusMap.get(ItemChartEnums.LEVEL_10.getLimit()))
                .ifPresent(census -> operator.setLevel10(census.getCensus()));
        Optional.ofNullable(censusMap.get(ItemChartEnums.LEVEL_20.getLimit()))
                .ifPresent(census -> operator.setLevel20(census.getCensus()));
        Optional.ofNullable(censusMap.get(ItemChartEnums.LEVEL_50.getLimit()))
                .ifPresent(census -> operator.setLevel50(census.getCensus()));
        Optional.ofNullable(censusMap.get(ItemChartEnums.LEVEL_100.getLimit()))
                .ifPresent(census -> operator.setLevel100(census.getCensus()));
        Optional.ofNullable(censusMap.get(ItemChartEnums.LEVEL_150.getLimit()))
                .ifPresent(census -> operator.setLevel150(census.getCensus()));
        return operator;
    }

}
