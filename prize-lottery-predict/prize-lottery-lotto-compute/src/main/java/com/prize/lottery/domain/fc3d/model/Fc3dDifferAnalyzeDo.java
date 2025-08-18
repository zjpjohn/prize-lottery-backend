package com.prize.lottery.domain.fc3d.model;

import com.google.common.collect.Lists;
import com.prize.lottery.domain.value.N3DifferValue;
import com.prize.lottery.po.fc3d.Fc3dIcaiPo;
import com.prize.lottery.value.AnalyzeValue;
import com.prize.lottery.value.ForecastValue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Data
public class Fc3dDifferAnalyzeDo {

    private List<ForecastValue> high7;
    private List<ForecastValue> high6;
    private List<ForecastValue> high3;
    private List<ForecastValue> low6;
    private List<ForecastValue> low5;
    private List<ForecastValue> low3;
    private List<ForecastValue> low2;
    private List<ForecastValue> low1;

    public Fc3dDifferAnalyzeDo(Integer limit,
                               List<Fc3dIcaiPo> com7s,
                               List<Fc3dIcaiPo> com6s,
                               List<Fc3dIcaiPo> dan3s,
                               List<ForecastValue> low6,
                               List<ForecastValue> low5,
                               List<ForecastValue> low3,
                               List<ForecastValue> low2,
                               List<ForecastValue> low1) {
        this.low6  = low6;
        this.low5  = low5;
        this.low3  = low3;
        this.low2  = low2;
        this.low1  = low1;
        this.high7 = com7s.stream().limit(limit).map(Fc3dIcaiPo::getCom7).collect(Collectors.toList());
        this.high6 = com6s.stream().limit(limit).map(Fc3dIcaiPo::getCom6).collect(Collectors.toList());
        this.high3 = dan3s.stream().limit(limit).map(Fc3dIcaiPo::getDan3).collect(Collectors.toList());
    }

    public N3DifferValue calculate() {

        //增量计算
        N3DifferValue differ = new N3DifferValue();
        differ.setDiffer76(differAnalyze(high7, low6));
        differ.setDiffer75(differAnalyze(high7, low5));
        differ.setDiffer73(differAnalyze(high7, low3));
        differ.setDiffer65(differAnalyze(high6, low5));
        differ.setDiffer63(differAnalyze(high6, low3));
        differ.setDiffer62(differAnalyze(high6, low2));
        differ.setDiffer32(differAnalyze(high3, low2));
        differ.setDiffer31(differAnalyze(high3, low1));

        //计算分析
        List<AnalyzeValue> picked = Lists.newArrayList();
        picked.addAll(picked(differ.getDiffer76()));
        picked.addAll(picked(differ.getDiffer75()));
        picked.addAll(picked(differ.getDiffer73()));
        picked.addAll(picked(differ.getDiffer65()));
        picked.addAll(picked(differ.getDiffer63()));
        picked.addAll(picked(differ.getDiffer62()));
        picked.addAll(picked(differ.getDiffer32()));
        picked.addAll(picked(differ.getDiffer31()));
        List<AnalyzeValue> values = picked.stream()
                                          .collect(Collectors.groupingBy(AnalyzeValue::getKey, Collectors.summingLong(AnalyzeValue::getValue)))
                                          .entrySet()
                                          .stream()
                                          .sorted((o1, o2) -> (int) (o2.getValue() - o1.getValue()))
                                          .map(e -> AnalyzeValue.of(e.getKey(), e.getValue()))
                                          .collect(Collectors.toList());
        differ.setValues(values);

        return differ;
    }

    private List<AnalyzeValue> picked(List<AnalyzeValue> data) {
        List<AnalyzeValue> picked = Lists.newArrayList();
        //取前段4个
        for (int i = 0; i < 5; i++) {
            AnalyzeValue value  = data.get(i);
            long         weight = i < 2 ? 7 : 3;
            picked.add(AnalyzeValue.of(value.getKey(), weight));
        }
        //取后段2个
        for (int i = 0; i < 2; i++) {
            AnalyzeValue value = data.get(data.size() - 1 - i);
            picked.add(AnalyzeValue.of(value.getKey(), 5L));
        }
        return picked;
    }

    /**
     * @param high 高命中率数据
     * @param low  低命中率数据
     */
    private List<AnalyzeValue> differAnalyze(List<ForecastValue> high, List<ForecastValue> low) {

        Map<String, Long> highCensus = high.stream()
                                           .flatMap(e -> e.split().stream())
                                           .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<String, Long> lowCensus = low.stream()
                                         .flatMap(e -> e.split().stream())
                                         .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return highCensus.entrySet()
                         .stream()
                         .peek(entry -> entry.setValue(entry.getValue() - lowCensus.get(entry.getKey())))
                         .sorted((o1, o2) -> (int) (o2.getValue() - o1.getValue()))
                         .map(entry -> AnalyzeValue.of(entry.getKey(), entry.getValue()))
                         .collect(Collectors.toList());
    }

}
