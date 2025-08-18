package com.prize.lottery.application.command.executor.lotto.pls;

import com.cloud.arch.utils.CollectionUtils;
import com.prize.lottery.application.vo.N3Comb5StatsVo;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.Pl3IcaiMapper;
import com.prize.lottery.po.pl3.Pl3IcaiPo;
import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.vo.pl3.Pl3IcaiDataVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class Pl3Comb5StatsExe {

    private final Pl3IcaiMapper mapper;

    public N3Comb5StatsVo execute(String period,
                                  Boolean containZu3,
                                  String dan,
                                  List<String> killList,
                                  List<Integer> kuaList,
                                  List<Integer> sums) {
        String              last     = LotteryEnum.PL3.lastPeriod(period);
        List<Pl3IcaiDataVo> dataList = mapper.getPl3RankedDataList(Fc3dChannel.COMB5.getChannel(), period, last, 20);
        if (CollectionUtils.isEmpty(dataList)) {
            return null;
        }
        Boolean contains = Optional.ofNullable(containZu3).orElse(true);
        List<N3Comb5StatsVo.StatsInfo> statsList = dataList.stream()
                                                           .map(Pl3IcaiPo::getComb5)
                                                           .map(ForecastValue::getData)
                                                           .flatMap(e -> N3Comb5StatsVo.flatLottery(e, contains))
                                                           .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                                                           .entrySet()
                                                           .stream()
                                                           .filter(entry -> entry.getValue() <= 4)
                                                           .map(entry -> new N3Comb5StatsVo.StatsInfo(entry.getKey(), entry.getValue()))
                                                           .collect(Collectors.toList());
        N3Comb5StatsVo comb5Stats = new N3Comb5StatsVo(statsList);
        comb5Stats.filter(dan, killList, kuaList, sums);
        return comb5Stats;
    }

}
