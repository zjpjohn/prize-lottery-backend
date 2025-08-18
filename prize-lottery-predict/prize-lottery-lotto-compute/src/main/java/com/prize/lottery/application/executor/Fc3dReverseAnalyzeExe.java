package com.prize.lottery.application.executor;

import com.google.common.collect.Lists;
import com.prize.lottery.application.vo.Fc3dReverseAnalyzeVo;
import com.prize.lottery.domain.fc3d.model.Fc3dReverseAnalyzeDo;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.mapper.Fc3dIcaiMapper;
import com.prize.lottery.po.fc3d.Fc3dIcaiPo;
import com.prize.lottery.utils.PeriodCalculator;
import com.prize.lottery.value.AnalyzeValue;
import com.prize.lottery.value.ForecastValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class Fc3dReverseAnalyzeExe {

    private final Fc3dIcaiMapper fc3dIcaiMapper;

    /**
     * 翻转分析
     *
     * @param period 计算期号
     */
    public Fc3dReverseAnalyzeVo execute(String period) {
        if (StringUtils.isBlank(period)) {
            period = fc3dIcaiMapper.latestFc3dIcaiPeriod().getPeriod();
        }
        String last = PeriodCalculator.fc3dPeriod(period, 1);

        Fc3dReverseAnalyzeVo reverseAnalyze = new Fc3dReverseAnalyzeVo();

        List<Fc3dIcaiPo>     dan3s       = fc3dIcaiMapper.getFc3dRankedItemDatas(Fc3dChannel.DAN3.value(), period, last);
        List<ForecastValue>  dan3List    = dan3s.stream().map(Fc3dIcaiPo::getDan3).collect(Collectors.toList());
        Fc3dReverseAnalyzeDo dan3Analyze = new Fc3dReverseAnalyzeDo(9, dan3List);
        List<AnalyzeValue>   dan3Census  = dan3Analyze.calculate();
        reverseAnalyze.setDan3s(dan3Census);

        List<Fc3dIcaiPo>     com5s       = fc3dIcaiMapper.getFc3dRankedItemDatas(Fc3dChannel.COM5.value(), period, last);
        List<ForecastValue>  com5List    = com5s.stream().map(Fc3dIcaiPo::getCom5).collect(Collectors.toList());
        Fc3dReverseAnalyzeDo com5Analyze = new Fc3dReverseAnalyzeDo(6, com5List);
        List<AnalyzeValue>   com5Census  = com5Analyze.calculate();
        reverseAnalyze.setCom5s(com5Census);

        List<Fc3dIcaiPo>     com6s       = fc3dIcaiMapper.getFc3dRankedItemDatas(Fc3dChannel.COM6.value(), period, last);
        List<ForecastValue>  com6List    = com6s.stream().map(Fc3dIcaiPo::getCom6).collect(Collectors.toList());
        Fc3dReverseAnalyzeDo com6Analyze = new Fc3dReverseAnalyzeDo(5, com6List);
        List<AnalyzeValue>   com6Census  = com6Analyze.calculate();
        reverseAnalyze.setCom6s(com6Census);

        List<Fc3dIcaiPo>     com7s       = fc3dIcaiMapper.getFc3dRankedItemDatas(Fc3dChannel.COM7.value(), period, last);
        List<ForecastValue>  com7List    = com7s.stream().map(Fc3dIcaiPo::getCom7).collect(Collectors.toList());
        Fc3dReverseAnalyzeDo com7Analyze = new Fc3dReverseAnalyzeDo(4, com7List);
        List<AnalyzeValue>   com7Census  = com7Analyze.calculate();
        reverseAnalyze.setCom7s(com7Census);

        List<AnalyzeValue> census = Lists.newArrayList();
        census.addAll(dan3Census.subList(0, 5));
        census.addAll(com5Census.subList(0, 5));
        census.addAll(com6Census.subList(0, 5));
        census.addAll(com7Census.subList(0, 5));
        List<AnalyzeValue> values = census.stream()
                                          .map(AnalyzeValue::getKey)
                                          .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                                          .entrySet()
                                          .stream()
                                          .sorted((o1, o2) -> (int) (o2.getValue() - o1.getValue()))
                                          .map(e -> AnalyzeValue.of(e.getKey(), e.getValue()))
                                          .collect(Collectors.toList());
        reverseAnalyze.setValues(values);

        return reverseAnalyze;
    }
}
