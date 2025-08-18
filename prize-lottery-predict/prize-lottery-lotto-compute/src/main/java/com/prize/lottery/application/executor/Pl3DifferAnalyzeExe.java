package com.prize.lottery.application.executor;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.application.vo.N3DifferAnalyzeVo;
import com.prize.lottery.domain.pl3.model.Pl3DifferAnalyzeDo;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.Pl3Channel;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.mapper.Pl3IcaiMapper;
import com.prize.lottery.po.pl3.Pl3IcaiPo;
import com.prize.lottery.value.AnalyzeValue;
import com.prize.lottery.value.ForecastValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class Pl3DifferAnalyzeExe {

    private final Pl3IcaiMapper mapper;

    public N3DifferAnalyzeVo execute(String period) {
        if (StringUtils.isBlank(period)) {
            period = Assert.notNull(mapper.latestPl3ICaiPeriod(), ResponseHandler.NO_FORECAST_DATA).getPeriod();
        }
        String last = LotteryEnum.PL3.lastPeriod(period);

        List<Pl3IcaiPo> com7s = mapper.getPl3RankdedItemDatas(Pl3Channel.COM7.value(), period, last);
        List<Pl3IcaiPo> com6s = mapper.getPl3RankdedItemDatas(Pl3Channel.COM6.value(), period, last);
        List<Pl3IcaiPo> com5s = mapper.getPl3RankdedItemDatas(Pl3Channel.COM5.value(), period, last);
        List<Pl3IcaiPo> dan3s = mapper.getPl3RankdedItemDatas(Pl3Channel.DAN3.value(), period, last);
        List<Pl3IcaiPo> dan2s = mapper.getPl3RankdedItemDatas(Pl3Channel.DAN2.value(), period, last);
        List<Pl3IcaiPo> dan1s = mapper.getPl3RankdedItemDatas(Pl3Channel.DAN1.value(), period, last);

        //低命中率预测数据
        List<ForecastValue> low6 = com6s.stream()
                                        .skip(com6s.size() - 200)
                                        .map(Pl3IcaiPo::getCom6)
                                        .collect(Collectors.toList());
        List<ForecastValue> low5 = com5s.stream()
                                        .skip(com6s.size() - 200)
                                        .map(Pl3IcaiPo::getCom5)
                                        .collect(Collectors.toList());
        List<ForecastValue> low3 = dan3s.stream()
                                        .skip(com6s.size() - 200)
                                        .map(Pl3IcaiPo::getDan3)
                                        .collect(Collectors.toList());
        List<ForecastValue> low2 = dan2s.stream()
                                        .skip(com6s.size() - 200)
                                        .map(Pl3IcaiPo::getDan2)
                                        .collect(Collectors.toList());
        List<ForecastValue> low1 = dan1s.stream()
                                        .skip(com6s.size() - 200)
                                        .map(Pl3IcaiPo::getDan1)
                                        .collect(Collectors.toList());

        //增量计算
        N3DifferAnalyzeVo differAnalyze = new N3DifferAnalyzeVo();

        Pl3DifferAnalyzeDo differ200 = new Pl3DifferAnalyzeDo(200, com7s, com6s, dan3s, low6, low5, low3, low2, low1);
        differAnalyze.setDiffer200(differ200.calculate());

        Pl3DifferAnalyzeDo differ500 = new Pl3DifferAnalyzeDo(500, com7s, com6s, dan3s, low6, low5, low3, low2, low1);
        differAnalyze.setDiffer500(differ500.calculate());

        Pl3DifferAnalyzeDo differ1000 = new Pl3DifferAnalyzeDo(1000, com7s, com6s, dan3s, low6, low5, low3, low2, low1);
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
