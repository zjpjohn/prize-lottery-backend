package com.prize.lottery.application.executor;

import com.google.common.collect.Lists;
import com.prize.lottery.application.vo.N3DifferAnalyzeVo;
import com.prize.lottery.domain.fc3d.model.Fc3dDifferAnalyzeDo;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.mapper.Fc3dIcaiMapper;
import com.prize.lottery.po.fc3d.Fc3dIcaiPo;
import com.prize.lottery.utils.PeriodCalculator;
import com.prize.lottery.value.AnalyzeValue;
import com.prize.lottery.value.ForecastValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Fc3dDifferAnalyzeExe {

    private final Fc3dIcaiMapper fc3dIcaiMapper;

    public Fc3dDifferAnalyzeExe(Fc3dIcaiMapper fc3dIcaiMapper) {
        this.fc3dIcaiMapper = fc3dIcaiMapper;
    }

    /**
     * 增量计算
     *
     * @param period 计算期号
     */
    public N3DifferAnalyzeVo execute(String period) {
        if (StringUtils.isBlank(period)) {
            period = fc3dIcaiMapper.latestFc3dIcaiPeriod().getPeriod();
        }
        //上一期期号
        String last = PeriodCalculator.fc3dPeriod(period, 1);

        //
        List<Fc3dIcaiPo> com7s = fc3dIcaiMapper.getFc3dRankedItemDatas(Fc3dChannel.COM7.value(), period, last);
        List<Fc3dIcaiPo> com6s = fc3dIcaiMapper.getFc3dRankedItemDatas(Fc3dChannel.COM6.value(), period, last);
        List<Fc3dIcaiPo> com5s = fc3dIcaiMapper.getFc3dRankedItemDatas(Fc3dChannel.COM5.value(), period, last);
        List<Fc3dIcaiPo> dan3s = fc3dIcaiMapper.getFc3dRankedItemDatas(Fc3dChannel.DAN3.value(), period, last);
        List<Fc3dIcaiPo> dan2s = fc3dIcaiMapper.getFc3dRankedItemDatas(Fc3dChannel.DAN2.value(), period, last);
        List<Fc3dIcaiPo> dan1s = fc3dIcaiMapper.getFc3dRankedItemDatas(Fc3dChannel.DAN1.value(), period, last);

        //低命中率预测数据
        List<ForecastValue> low6 = com6s.stream()
                                        .skip(com6s.size() - 200)
                                        .map(Fc3dIcaiPo::getCom6)
                                        .collect(Collectors.toList());
        List<ForecastValue> low5 = com5s.stream()
                                        .skip(com6s.size() - 200)
                                        .map(Fc3dIcaiPo::getCom5)
                                        .collect(Collectors.toList());
        List<ForecastValue> low3 = dan3s.stream()
                                        .skip(com6s.size() - 200)
                                        .map(Fc3dIcaiPo::getDan3)
                                        .collect(Collectors.toList());
        List<ForecastValue> low2 = dan2s.stream()
                                        .skip(com6s.size() - 200)
                                        .map(Fc3dIcaiPo::getDan2)
                                        .collect(Collectors.toList());
        List<ForecastValue> low1 = dan1s.stream()
                                        .skip(com6s.size() - 200)
                                        .map(Fc3dIcaiPo::getDan1)
                                        .collect(Collectors.toList());

        //增量计算
        N3DifferAnalyzeVo differAnalyze = new N3DifferAnalyzeVo();

        Fc3dDifferAnalyzeDo differ200 = new Fc3dDifferAnalyzeDo(200, com7s, com6s, dan3s, low6, low5, low3, low2, low1);
        differAnalyze.setDiffer200(differ200.calculate());

        Fc3dDifferAnalyzeDo differ500 = new Fc3dDifferAnalyzeDo(500, com7s, com6s, dan3s, low6, low5, low3, low2, low1);
        differAnalyze.setDiffer500(differ500.calculate());

        Fc3dDifferAnalyzeDo differ1000 = new Fc3dDifferAnalyzeDo(1000, com7s, com6s, dan3s, low6, low5, low3, low2, low1);
        differAnalyze.setDiffer1000(differ1000.calculate());

        //整体综合计算
        List<AnalyzeValue> census = Lists.newArrayList();
        census.addAll(differAnalyze.getDiffer200().getValues());
        census.addAll(differAnalyze.getDiffer500().getValues());
        census.addAll(differAnalyze.getDiffer1000().getValues());

        List<AnalyzeValue> values = census.stream()
                                          .collect(Collectors.groupingBy(AnalyzeValue::getKey, Collectors.summingLong(AnalyzeValue::getValue)))
                                          .entrySet()
                                          .stream()
                                          .sorted((o1, o2) -> (int) (o2.getValue() - o1.getValue()))
                                          .map(e -> AnalyzeValue.of(e.getKey(), e.getValue()))
                                          .collect(Collectors.toList());
        differAnalyze.setValues(values);

        return differAnalyze;
    }

}
