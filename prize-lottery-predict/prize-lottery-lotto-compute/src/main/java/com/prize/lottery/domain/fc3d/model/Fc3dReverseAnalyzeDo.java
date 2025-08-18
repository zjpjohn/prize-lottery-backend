package com.prize.lottery.domain.fc3d.model;

import com.cloud.arch.utils.CollectionUtils;
import com.google.common.collect.Lists;
import com.prize.lottery.value.AnalyzeValue;
import com.prize.lottery.value.ForecastValue;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class Fc3dReverseAnalyzeDo {

    public static final String[] FC3D_BALLS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    private final Integer            segSize;
    private final Integer            segments;
    private final List<List<String>> values;

    public Fc3dReverseAnalyzeDo(Integer segSize, List<ForecastValue> values) {
        this.segSize  = segSize;
        this.values   = values.stream().map(ForecastValue::split).collect(Collectors.toList());
        this.segments = (values.size() % segSize == 0) ? (values.size() / segSize) : (values.size() / segSize + 1);
    }

    public List<AnalyzeValue> calculate() {
        List<String> result = Lists.newArrayList();
        for (int i = 1; i <= segments; i++) {
            int startIndex = (i - 1) * segSize;
            List<String> collect = values.stream()
                                         .skip(startIndex)
                                         .limit(segSize)
                                         .flatMap(List::stream)
                                         .distinct()
                                         .toList();
            List<String> alls = Lists.newArrayList(FC3D_BALLS);
            alls.removeAll(collect);
            if (CollectionUtils.isNotEmpty(alls)) {
                result.addAll(alls);
            }
        }
        return result.stream()
                     .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                     .entrySet()
                     .stream()
                     .sorted((o1, o2) -> (int) (o2.getValue() - o1.getValue()))
                     .map(e -> AnalyzeValue.of(e.getKey(), e.getValue()))
                     .collect(Collectors.toList());
    }
}
