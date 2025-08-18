package com.prize.lottery.enums;

import com.cloud.arch.enums.Value;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.prize.lottery.utils.ICaiConstants;
import com.prize.lottery.value.CensusValue;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public interface ChartCalculator extends Value<Integer> {

    /**
     * 单值统计
     *
     * @param data
     * @param keys
     */
    default CensusValue calcPlain(List<String> data, List<String> keys) {
        Map<String, Long> census = data.stream().limit(this.value())
                .flatMap(e -> Splitter.on(Pattern.compile("\\s+"))
                        .trimResults()
                        .omitEmptyStrings()
                        .splitToStream(e))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        ICaiConstants.unionPadding(census, keys);
        census = new TreeMap<>(census);
        return new CensusValue(Lists.newArrayList(census.values()));
    }

    /**
     * 复合值统计
     *
     * @param data
     * @param keys
     */
    default CensusValue calcDuplex(List<String> data, List<String> keys) {
        Map<String, Long> hiStc = data.stream()
                .limit(value())
                .map(item -> item.split("\\*")[0])
                .flatMap(item -> Splitter.on(Pattern.compile("\\s+"))
                        .trimResults()
                        .omitEmptyStrings()
                        .splitToStream(item))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        ICaiConstants.unionPadding(hiStc, keys);
        hiStc = new TreeMap<>(hiStc);
        Map<String, Long> miStc = data.stream()
                .limit(value())
                .map(item -> item.split("\\*")[1])
                .flatMap(item -> Splitter.on(Pattern.compile("\\s+"))
                        .trimResults()
                        .omitEmptyStrings()
                        .splitToStream(item))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        ICaiConstants.unionPadding(miStc, keys);
        miStc = new TreeMap<>(miStc);
        Map<String, Long> lowStc = data.stream()
                .limit(value())
                .map(item -> item.split("\\*")[2])
                .flatMap(item -> Splitter.on(Pattern.compile("\\s+"))
                        .trimResults()
                        .omitEmptyStrings()
                        .splitToStream(item))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        ICaiConstants.unionPadding(lowStc, keys);
        lowStc = new TreeMap<>(lowStc);
        return new CensusValue(Lists.newArrayList(hiStc.values()),
                Lists.newArrayList(miStc.values()),
                Lists.newArrayList(lowStc.values()));
    }

}
