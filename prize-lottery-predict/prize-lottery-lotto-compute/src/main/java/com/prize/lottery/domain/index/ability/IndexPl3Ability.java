package com.prize.lottery.domain.index.ability;

import com.cloud.arch.utils.IdWorker;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.Pl3Channel;
import com.prize.lottery.mapper.LotteryIndexMapper;
import com.prize.lottery.mapper.Num3LottoIndexMapper;
import com.prize.lottery.mapper.Pl3IcaiMapper;
import com.prize.lottery.po.lottery.LotteryIndexPo;
import com.prize.lottery.po.lottery.Num3LottoIndexPo;
import com.prize.lottery.po.pl3.Pl3LottoCensusPo;
import com.prize.lottery.value.BallIndex;
import com.prize.lottery.value.LottoIndex;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class IndexPl3Ability implements IndexAbility {

    private final Pl3IcaiMapper        icaiMapper;
    private final LotteryIndexMapper   indexMapper;
    private final Num3LottoIndexMapper num3IndexMapper;

    @Override
    public LotteryEnum bizIndex() {
        return LotteryEnum.PL3;
    }

    @Override
    public void calcIndex(String period) {
        List<Pl3LottoCensusPo> censusList = icaiMapper.getTypedPl3CensusList(period, ChartType.ITEM_CHART.getType());
        if (CollectionUtils.isEmpty(censusList)) {
            return;
        }
        Map<Integer, List<Pl3LottoCensusPo>> leveledCensus = censusList.stream()
                                                                       .collect(Collectors.groupingBy(Pl3LottoCensusPo::getLevel));
        LottoIndex     lottoIndex = leveledItemIndex(leveledCensus, this::calcBallIndex);
        LotteryIndexPo index      = new LotteryIndexPo();
        index.setType(2);
        index.setPeriod(period);
        index.setLottery(LotteryEnum.PL3);
        index.setRedBall(lottoIndex);
        indexMapper.addLotteryIndex(index);
    }

    @Override
    public void calcItemIndex(String period) {
        List<Pl3LottoCensusPo> censusList = icaiMapper.getTypedPl3CensusList(period, ChartType.ITEM_CHART.getType());
        if (CollectionUtils.isEmpty(censusList)) {
            return;
        }
        Map<Integer, List<Pl3LottoCensusPo>> leveledCensus = censusList.stream()
                                                                       .collect(Collectors.groupingBy(Pl3LottoCensusPo::getLevel));
        LottoIndex       danIndex   = leveledItemIndex(leveledCensus, this::calcDanCensus);
        LottoIndex       killIndex  = leveledItemIndex(leveledCensus, this::calcKillCensus);
        LottoIndex       comIndex   = leveledItemIndex(leveledCensus, this::calcComCensus);
        Num3LottoIndexPo lottoIndex = new Num3LottoIndexPo();
        lottoIndex.setPeriod(period);
        lottoIndex.setId(IdWorker.nextId());
        lottoIndex.setType(LotteryEnum.PL3);
        lottoIndex.setDanIndex(danIndex);
        lottoIndex.setKillIndex(killIndex);
        lottoIndex.setComIndex(comIndex);
        num3IndexMapper.addNum3LottoIndex(lottoIndex);
    }

    private LottoIndex leveledItemIndex(Map<Integer, List<Pl3LottoCensusPo>> leveledCensus,
                                        Function<Map<Pl3Channel, Pl3LottoCensusPo>, Pair<List<Integer>, List<Integer>>> function) {
        Map<String, BallIndex> index10 = itemIndex(leveledCensus.get(10), function).stream()
                                                                                   .collect(Collectors.toMap(BallIndex::getBall, v -> v));
        Map<String, BallIndex> index20 = itemIndex(leveledCensus.get(20), function).stream()
                                                                                   .collect(Collectors.toMap(BallIndex::getBall, v -> v));
        Map<String, BallIndex> index50 = itemIndex(leveledCensus.get(50), function).stream()
                                                                                   .collect(Collectors.toMap(BallIndex::getBall, v -> v));
        Map<String, BallIndex> index100 = itemIndex(leveledCensus.get(100), function).stream()
                                                                                     .collect(Collectors.toMap(BallIndex::getBall, v -> v));
        List<BallIndex> indices = Lists.newArrayList();
        index50.forEach((key, value) -> {
            BallIndex ball10  = index10.get(key);
            BallIndex ball20  = index20.get(key);
            BallIndex ball100 = index100.get(key);
            double    index   = (ball20.getIndex() + value.getIndex() + ball100.getIndex() + ball10.getIndex()) / 4.0;
            index = BigDecimal.valueOf(index).setScale(2, RoundingMode.HALF_UP).doubleValue();
            indices.add(new BallIndex(key, index));
        });
        return new LottoIndex(indices).sort();
    }

    private List<BallIndex> itemIndex(List<Pl3LottoCensusPo> censusList,
                                      Function<Map<Pl3Channel, Pl3LottoCensusPo>, Pair<List<Integer>, List<Integer>>> function) {
        Map<Pl3Channel, Pl3LottoCensusPo> censusMap = censusList.stream()
                                                                .collect(Collectors.toMap(Pl3LottoCensusPo::getChannel, Function.identity()));
        Pair<List<Integer>, List<Integer>> pair = function.apply(censusMap);
        return calcNum3Index(pair.getKey(), pair.getValue(), 4);
    }

    private Pair<List<Integer>, List<Integer>> calcDanCensus(Map<Pl3Channel, Pl3LottoCensusPo> censusMap) {
        List<Integer> bestSets = Lists.newArrayList();
        List<Integer> badSets  = Lists.newArrayList();
        //独胆胆
        Pl3LottoCensusPo d1Census = censusMap.get(Pl3Channel.DAN1);
        calcCensus(d1Census, 3, 2, bestSets, badSets, false);
        //双胆
        Pl3LottoCensusPo d2Census = censusMap.get(Pl3Channel.DAN2);
        calcCensus(d2Census, 3, 2, bestSets, badSets, false);
        //三胆
        Pl3LottoCensusPo d3Census = censusMap.get(Pl3Channel.DAN3);
        calcCensus(d3Census, 3, 2, bestSets, badSets, false);
        return Pair.of(bestSets, badSets);
    }

    private Pair<List<Integer>, List<Integer>> calcKillCensus(Map<Pl3Channel, Pl3LottoCensusPo> censusMap) {
        List<Integer> bestSets = Lists.newArrayList();
        List<Integer> badSets  = Lists.newArrayList();
        //杀一码
        Pl3LottoCensusPo k1Census = censusMap.get(Pl3Channel.KILL1);
        calcCensus(k1Census, 3, 2, bestSets, badSets, true);
        //杀二码
        Pl3LottoCensusPo k2Census = censusMap.get(Pl3Channel.KILL2);
        calcCensus(k2Census, 3, 2, bestSets, badSets, true);
        return Pair.of(bestSets, badSets);
    }

    private Pair<List<Integer>, List<Integer>> calcComCensus(Map<Pl3Channel, Pl3LottoCensusPo> censusMap) {
        List<Integer> bestSets = Lists.newArrayList();
        List<Integer> badSets  = Lists.newArrayList();
        //五码
        Pl3LottoCensusPo c5Census = censusMap.get(Pl3Channel.COM5);
        calcCensus(c5Census, 3, 2, bestSets, badSets, false);
        //六码
        Pl3LottoCensusPo c6Census = censusMap.get(Pl3Channel.COM6);
        calcCensus(c6Census, 3, 2, bestSets, badSets, false);
        //七码
        Pl3LottoCensusPo c7Census = censusMap.get(Pl3Channel.COM7);
        calcCensus(c7Census, 3, 2, bestSets, badSets, false);
        return Pair.of(bestSets, badSets);
    }

    private Pair<List<Integer>, List<Integer>> calcBallIndex(Map<Pl3Channel, Pl3LottoCensusPo> censusMap) {
        List<Integer> bestSets = Lists.newArrayList();
        List<Integer> badSets  = Lists.newArrayList();

        Pair<List<Integer>, List<Integer>> danIndex = calcDanCensus(censusMap);
        bestSets.addAll(danIndex.getKey());
        badSets.addAll(danIndex.getValue());

        Pair<List<Integer>, List<Integer>> killIndex = calcKillCensus(censusMap);
        bestSets.addAll(killIndex.getKey());
        badSets.addAll(killIndex.getValue());

        Pair<List<Integer>, List<Integer>> comIndex = calcComCensus(censusMap);
        bestSets.addAll(comIndex.getKey());
        badSets.addAll(comIndex.getValue());

        //计算指数
        return Pair.of(bestSets, badSets);
    }

    public List<BallIndex> vipBallIndex(String period) {
        List<Pl3LottoCensusPo> censusList = icaiMapper.getTypedPl3CensusList(period, ChartType.VIP_CHART.getType());
        if (CollectionUtils.isEmpty(censusList)) {
            return Collections.emptyList();
        }
        Map<Integer, List<Pl3LottoCensusPo>> leveledCensus = censusList.stream()
                                                                       .collect(Collectors.groupingBy(Pl3LottoCensusPo::getLevel));
        List<String> result = Lists.newArrayList();
        result.addAll(calcLevelIndex(leveledCensus.get(10)));
        result.addAll(calcLevelIndex(leveledCensus.get(20)));
        result.addAll(calcLevelIndex(leveledCensus.get(50)));
        result.addAll(calcLevelIndex(leveledCensus.get(100)));
        result.addAll(calcLevelIndex(leveledCensus.get(150)));

        Map<String, Long> groupCount = result.stream()
                                             .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return groupCount.entrySet()
                         .stream()
                         .map(entry -> new BallIndex(entry.getKey(), entry.getValue() / 25.0))
                         .sorted(Comparator.reverseOrder())
                         .collect(Collectors.toList());
    }

    public List<BallIndex> fullBallIndex(String period) {
        List<Pl3LottoCensusPo> censusList = icaiMapper.getTypedPl3CensusList(period, ChartType.ALL_CHART.getType());
        if (CollectionUtils.isEmpty(censusList)) {
            return Collections.emptyList();
        }
        Map<Integer, List<Pl3LottoCensusPo>> leveledCensus = censusList.stream()
                                                                       .collect(Collectors.groupingBy(Pl3LottoCensusPo::getLevel));
        List<String> result = Lists.newArrayList();
        result.addAll(calcLevelIndex(leveledCensus.get(100)));
        result.addAll(calcLevelIndex(leveledCensus.get(200)));
        result.addAll(calcLevelIndex(leveledCensus.get(400)));
        result.addAll(calcLevelIndex(leveledCensus.get(600)));
        result.addAll(calcLevelIndex(leveledCensus.get(800)));

        Map<String, Long> groupCount = result.stream()
                                             .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return groupCount.entrySet()
                         .stream()
                         .map(entry -> new BallIndex(entry.getKey(), entry.getValue() / 25.0))
                         .sorted(Comparator.reverseOrder())
                         .collect(Collectors.toList());
    }

    private List<String> calcLevelIndex(List<Pl3LottoCensusPo> censusList) {
        Map<Pl3Channel, Pl3LottoCensusPo> channelMap = Maps.uniqueIndex(censusList, Pl3LottoCensusPo::getChannel);
        List<String>                      result     = Lists.newArrayList();
        List<String> com5 = Objects.requireNonNull(channelMap.get(Pl3Channel.COM5))
                                   .getCensus()
                                   .singleTo(true)
                                   .descendingMap()
                                   .entrySet()
                                   .stream()
                                   .limit(4)
                                   .flatMap(entry -> entry.getValue().stream())
                                   .collect(Collectors.toList());
        result.addAll(com5);
        List<String> com6 = Objects.requireNonNull(channelMap.get(Pl3Channel.COM6))
                                   .getCensus()
                                   .singleTo(true)
                                   .descendingMap()
                                   .entrySet()
                                   .stream()
                                   .limit(4)
                                   .flatMap(entry -> entry.getValue().stream())
                                   .collect(Collectors.toList());
        result.addAll(com6);
        List<String> com7 = Objects.requireNonNull(channelMap.get(Pl3Channel.COM7))
                                   .getCensus()
                                   .singleTo(true)
                                   .descendingMap()
                                   .entrySet()
                                   .stream()
                                   .limit(4)
                                   .flatMap(entry -> entry.getValue().stream())
                                   .collect(Collectors.toList());
        result.addAll(com7);
        List<String> kill1 = Objects.requireNonNull(channelMap.get(Pl3Channel.KILL1))
                                    .getCensus()
                                    .singleTo(true)
                                    .entrySet()
                                    .stream()
                                    .limit(4)
                                    .flatMap(entry -> entry.getValue().stream())
                                    .collect(Collectors.toList());
        result.addAll(kill1);
        List<String> kill2 = Objects.requireNonNull(channelMap.get(Pl3Channel.KILL2))
                                    .getCensus()
                                    .singleTo(true)
                                    .entrySet()
                                    .stream()
                                    .limit(4)
                                    .flatMap(entry -> entry.getValue().stream())
                                    .collect(Collectors.toList());
        result.addAll(kill2);
        return result;
    }

}
