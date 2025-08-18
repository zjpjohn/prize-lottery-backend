package com.prize.lottery.application.executor;

import com.google.common.collect.Lists;
import com.prize.lottery.application.vo.Fc3dCombineAnalyzeVo;
import com.prize.lottery.domain.fc3d.model.Fc3dCombineAnalyzeDo;
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
public class Fc3dCombineAnalyzeExe {

    private final Fc3dIcaiMapper fc3dIcaiMapper;

    /**
     * 组合计算
     *
     * @param period 计算期号
     */
    public Fc3dCombineAnalyzeVo execute(String period) {
        if (StringUtils.isBlank(period)) {
            period = fc3dIcaiMapper.latestFc3dIcaiPeriod().getPeriod();
        }
        String last = PeriodCalculator.fc3dPeriod(period, 1);

        Fc3dCombineAnalyzeVo combineAnalyze = new Fc3dCombineAnalyzeVo();

        //五码组合计算
        List<Fc3dIcaiPo>     com5s        = fc3dIcaiMapper.getFc3dRankedItemDatas(Fc3dChannel.COM5.value(), period, last);
        List<ForecastValue>  com5Values   = com5s.stream().map(Fc3dIcaiPo::getCom5).collect(Collectors.toList());
        Fc3dCombineAnalyzeDo com5Analyzer = new Fc3dCombineAnalyzeDo(8, 7, com5Values);
        List<AnalyzeValue>   com5Census   = com5Analyzer.calculate();
        combineAnalyze.setCom5(com5Census);

        //六码组合计算
        List<Fc3dIcaiPo>     com6s        = fc3dIcaiMapper.getFc3dRankedItemDatas(Fc3dChannel.COM6.value(), period, last);
        List<ForecastValue>  com6Values   = com6s.stream().map(Fc3dIcaiPo::getCom6).collect(Collectors.toList());
        Fc3dCombineAnalyzeDo com6Analyzer = new Fc3dCombineAnalyzeDo(8, 8, com6Values);
        List<AnalyzeValue>   com6Census   = com6Analyzer.calculate();
        combineAnalyze.setCom6(com6Census);

        //七码组合计算
        List<Fc3dIcaiPo>     com7s        = fc3dIcaiMapper.getFc3dRankedItemDatas(Fc3dChannel.COM7.value(), period, last);
        List<ForecastValue>  com7Values   = com7s.stream().map(Fc3dIcaiPo::getCom7).collect(Collectors.toList());
        Fc3dCombineAnalyzeDo com7Analyzer = new Fc3dCombineAnalyzeDo(8, 8, com7Values);
        List<AnalyzeValue>   com7Census   = com7Analyzer.calculate();
        combineAnalyze.setCom7(com7Census);

        List<AnalyzeValue> census = Lists.newArrayList();
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
        combineAnalyze.setValues(values);

        return combineAnalyze;
    }
}
