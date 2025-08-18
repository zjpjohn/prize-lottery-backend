package com.prize.lottery.application.query.executor;

import com.google.common.base.Splitter;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.prize.lottery.application.vo.N3ItemBestTableVo;
import com.prize.lottery.application.vo.N3ItemCensusVo;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.Pl3Channel;
import com.prize.lottery.mapper.Pl3IcaiMapper;
import com.prize.lottery.vo.pl3.Pl3IcaiDataVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class Pl3ItemAnalyzeExe {

    protected static final List<String>  all = Lists.newArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    private final          Pl3IcaiMapper mapper;

    public N3ItemBestTableVo query(String period, Integer limit) {
        String last = LotteryEnum.PL3.lastPeriod(period);
        Map<Pl3Channel, N3ItemCensusVo.ItemCensus> collected = Arrays.stream(Pl3Channel.values())
                                                                     .filter(Pl3Channel::isSingle)
                                                                     .map(channel -> Pair.of(channel, calcCount(mapper.getPl3RankedDataList(channel.getChannel(), period, last, limit))))
                                                                     .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        N3ItemCensusVo.ItemCensus zong = calcCount(mapper.getMulRankDataList(period, last, limit));
        return N3ItemBestTableVo.pl3(collected, zong);
    }

    public N3ItemCensusVo execute(String period) {
        String last = LotteryEnum.PL3.lastPeriod(period);
        List<N3ItemCensusVo.ItemCensus> collected = Arrays.stream(Pl3Channel.values())
                                                          .filter(Pl3Channel::isVipChannel)
                                                          .map(channel -> calcCount(mapper.getPl3RankedDataList(channel.getChannel(), period, last, 15)))
                                                          .collect(Collectors.toList());
        collected.add(calcCount(mapper.getMulRankDataList(period, last, 15)));

        N3ItemCensusVo census = new N3ItemCensusVo();
        census.setPeriod(period);
        census.setK1(calcTotal(collected, N3ItemCensusVo.ItemCensus::getK1));
        census.setK2(calcTotal(collected, N3ItemCensusVo.ItemCensus::getK2));
        census.setD1(calcTotal(collected, N3ItemCensusVo.ItemCensus::getD1));
        census.setD2(calcTotal(collected, N3ItemCensusVo.ItemCensus::getD2));
        census.setD3(calcTotal(collected, N3ItemCensusVo.ItemCensus::getD3));
        census.setC7(calcTotal(collected, N3ItemCensusVo.ItemCensus::getC7));
        return census;
    }

    private Map<Long, List<N3ItemCensusVo.CensusCell>> calcTotal(List<N3ItemCensusVo.ItemCensus> data,
                                                                 Function<N3ItemCensusVo.ItemCensus, Multimap<Long, String>> extractor) {
        Multimap<Long, String> multimap = ArrayListMultimap.create();
        data.stream().map(extractor).forEach(multimap::putAll);
        Map<Long, List<N3ItemCensusVo.CensusCell>> result = Maps.newHashMap();
        Set<Long>                                  keySet = multimap.keySet();
        for (Long key : keySet) {
            List<N3ItemCensusVo.CensusCell> collect = multimap.get(key)
                                                              .stream()
                                                              .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                                                              .entrySet()
                                                              .stream()
                                                              .map(entry -> new N3ItemCensusVo.CensusCell(entry.getKey(), entry.getValue()))
                                                              .sorted(Comparator.comparing(N3ItemCensusVo.CensusCell::getCount, Comparator.reverseOrder()))
                                                              .collect(Collectors.toList());
            result.put(key, collect);
        }
        return result;
    }

    private N3ItemCensusVo.ItemCensus calcCount(List<Pl3IcaiDataVo> dataList) {
        N3ItemCensusVo.ItemCensus census = new N3ItemCensusVo.ItemCensus();
        census.setD1(this.calcCount(dataList, e -> e.getDan1().getData()));
        census.setD2(this.calcCount(dataList, e -> e.getDan2().getData()));
        census.setD3(this.calcCount(dataList, e -> e.getDan3().getData()));
        census.setC7(this.calcCount(dataList, e -> e.getCom7().getData()));
        census.setK1(this.calcCount(dataList, e -> e.getKill1().getData()));
        census.setK2(this.calcCount(dataList, e -> e.getKill2().getData()));
        return census;
    }

    private Multimap<Long, String> calcCount(List<Pl3IcaiDataVo> dataList, Function<Pl3IcaiDataVo, String> extractor) {
        Multimap<Long, String> multimap = ArrayListMultimap.create();
        List<String> collect = dataList.stream()
                                       .map(extractor)
                                       .flatMap(e -> Splitter.onPattern("\\s+").trimResults().splitToStream(e))
                                       .toList();
        collect.stream()
               .collect(Collectors.groupingBy(key -> key, Collectors.counting()))
               .forEach((key, value) -> multimap.put(value, key));
        all.stream().filter(e -> !collect.contains(e)).forEach(e -> multimap.put(0L, e));
        return multimap;
    }
}
