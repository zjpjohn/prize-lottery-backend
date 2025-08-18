package com.prize.lottery.domain.fc3d.model;

import com.google.common.collect.Lists;
import com.prize.lottery.value.AnalyzeValue;
import com.prize.lottery.value.ForecastValue;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class Fc3dCombineAnalyzeDo {

    private final Integer             segSize;
    private final Integer             throttle;
    private final Integer             segments;
    private final List<ForecastValue> values;

    public Fc3dCombineAnalyzeDo(Integer segSize, Integer throttle, List<ForecastValue> values) {
        this.segSize  = segSize;
        this.throttle = throttle;
        this.segments = (values.size() % segSize == 0) ? (values.size() / segSize) : (values.size() / segSize + 1);
        this.values   = values;
    }

    public List<AnalyzeValue> calculate() {
        List<String> results = Lists.newArrayList();
        for (int i = 1; i <= segments; i++) {
            int startIndex = (i - 1) * segSize;
            List<String> list = values.stream()
                                      .skip(startIndex)
                                      .limit(segSize)
                                      .flatMap(item -> item.split().stream())
                                      .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                                      .entrySet()
                                      .stream()
                                      .filter(entry -> entry.getValue() - throttle >= 0)
                                      .map(Map.Entry::getKey)
                                      .toList();
            results.addAll(list);
        }
        return results.stream()
                      .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                      .entrySet()
                      .stream()
                      .sorted((o1, o2) -> (int) (o2.getValue() - o1.getValue()))
                      .map(item -> AnalyzeValue.of(item.getKey(), item.getValue()))
                      .collect(Collectors.toList());
    }
}
