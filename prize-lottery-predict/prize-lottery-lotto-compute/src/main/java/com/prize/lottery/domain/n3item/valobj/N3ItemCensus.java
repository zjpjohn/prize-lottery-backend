package com.prize.lottery.domain.n3item.valobj;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class N3ItemCensus {

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

    public N3ItemCensus(List<N3ItemForecast> data) {
        period = data.get(0).getPeriod();
        //双胆指数
        d2Index = this.calcIndex(data, e -> e.getDan2().getData());
        //三胆指数
        d3Index = this.calcIndex(data, e -> e.getDan3().getData());
        //杀码指数
        k1Index = this.calcIndex(data, e -> e.getKill1().getData());
        //双胆杀比
        this.d2kRatio = this.calcDkRatio(this.d2Index);
        //三胆胆杀比
        this.d3kRatio = this.calcDkRatio(this.d3Index);
    }

    private Map<String, Double> calcIndex(List<N3ItemForecast> data, Function<N3ItemForecast, String> extractor) {
        Map<String, Long> collect = data.stream()
                                        .map(extractor)
                                        .flatMap(e -> Splitter.onPattern("\\s+").trimResults().splitToStream(e))
                                        .collect(Collectors.groupingBy(key -> key, Collectors.counting()));
        Long                maxValue = maxValue(collect.values());
        Map<String, Double> indices  = Maps.newHashMap();
        for (Map.Entry<String, Long> entry : collect.entrySet()) {
            double value =
                BigDecimal.valueOf(entry.getValue() * 1.0 / maxValue).setScale(2, RoundingMode.HALF_UP).doubleValue();
            indices.put(entry.getKey(), value);
        }
        return indices;
    }

    private Map<String, Double> calcDkRatio(Map<String, Double> indices) {
        Map<String, Double> ratio = Maps.newHashMap();
        indices.forEach((key, value) -> {
            double index    = Optional.ofNullable(k1Index.get(key)).filter(e -> e > 0).orElse(0.01);
            double ratioVal = BigDecimal.valueOf(value / index).setScale(2, RoundingMode.HALF_UP).doubleValue();
            ratio.put(key, ratioVal);
        });
        return ratio;
    }

    private Long maxValue(Collection<Long> values) {
        long max = 0L;
        for (Long value : values) {
            max = Math.max(value, max);
        }
        return max;
    }
}
