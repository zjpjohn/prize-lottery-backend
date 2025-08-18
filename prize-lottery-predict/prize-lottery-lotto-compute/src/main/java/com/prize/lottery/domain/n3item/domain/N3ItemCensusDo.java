package com.prize.lottery.domain.n3item.domain;

import com.google.common.collect.Maps;
import com.prize.lottery.domain.n3item.valobj.N3ItemCensus;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Getter
public class N3ItemCensusDo {

    //指定期号
    private final String              period;
    //双胆胆杀比率
    private final Map<String, Double> d2kRatio;
    //三胆胆杀比率
    private final Map<String, Double> d3kRatio;
    //双胆指数
    private final Map<String, Double> d2Index;
    //三胆指数
    private final Map<String, Double> d3Index;
    //杀码指数
    private final Map<String, Double> k1Index;

    public N3ItemCensusDo(List<N3ItemCensus> list) {
        this.period   = list.get(0).getPeriod();
        this.d2Index  = this.calcAverage(list, N3ItemCensus::getD2Index);
        this.d3Index  = this.calcAverage(list, N3ItemCensus::getD3Index);
        this.k1Index  = this.calcAverage(list, N3ItemCensus::getK1Index);
        this.d2kRatio = this.calcAverage(list, N3ItemCensus::getD2kRatio);
        this.d3kRatio = this.calcAverage(list, N3ItemCensus::getD3kRatio);
    }

    private Map<String, Double> calcAverage(List<N3ItemCensus> data,
        Function<N3ItemCensus, Map<String, Double>> extractor) {
        Map<String, Double> total = Maps.newHashMap();
        data.stream().map(extractor).flatMap(e -> e.entrySet().stream()).forEach(e -> {
            Double oldVal = Optional.ofNullable(total.get(e.getKey())).orElse(0.0);
            total.put(e.getKey(), oldVal + e.getValue());
        });
        Map<String, Double> indices = Maps.newHashMap();
        int                 size    = data.size();
        for (Map.Entry<String, Double> entry : total.entrySet()) {
            double value = BigDecimal.valueOf(entry.getValue() / size).setScale(2, RoundingMode.HALF_UP).doubleValue();
            indices.put(entry.getKey(), value);
        }
        return indices;
    }

}
