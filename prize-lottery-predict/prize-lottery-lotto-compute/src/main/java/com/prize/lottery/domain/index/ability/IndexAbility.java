package com.prize.lottery.domain.index.ability;

import com.cloud.arch.executor.Executor;
import com.google.common.collect.Maps;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.share.BaseLottoCensus;
import com.prize.lottery.value.BallIndex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface IndexAbility extends Executor<LotteryEnum> {

    /**
     * 计算整体指数
     */
    void calcIndex(String period);

    /**
     * 计算胆码、杀码、组选指数
     */
    void calcItemIndex(String period);

    default List<BallIndex> calcNum3Index(List<Integer> bestSets, List<Integer> badSets, Integer badThrottle) {
        //统计数据集频率
        Map<Integer, Long> bestCounts = bestSets.stream()
                                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<Integer, Long> badCounts = badSets.stream()
                                              .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<Integer, Long> counts = Maps.newLinkedHashMap();
        for (int i = 0; i <= 9; i++) {
            long count = 0L;
            long best  = Optional.ofNullable(bestCounts.get(i)).orElse(0L);
            long bad   = Optional.ofNullable(badCounts.get(i)).orElse(0L);
            count = count + Math.max(best + (badThrottle - bad), 0);
            counts.put(i, Math.min(count, 10));
        }
        return counts.entrySet().stream().map(entry -> {
            String ball  = entry.getKey().toString();
            double index = BigDecimal.valueOf(entry.getValue() / 10.0).setScale(2, RoundingMode.HALF_UP).doubleValue();
            return new BallIndex(ball, index);
        }).collect(Collectors.toList());
    }

    default void calcCensus(BaseLottoCensus census,
                            Integer bestLimit,
                            Integer badLimit,
                            List<Integer> best,
                            List<Integer> bad,
                            boolean reverse) {
        TreeMap<Long, List<Integer>> sort = census.getCensus().singleToMap();
        best.addAll(bestOrBadThree(sort, bestLimit, !reverse));
        bad.addAll(bestOrBadThree(sort, badLimit, reverse));
    }

    default List<Integer> bestOrBadThree(TreeMap<Long, List<Integer>> dataSet, Integer limit, Boolean best) {
        if (best) {
            return dataSet.descendingMap()
                          .entrySet()
                          .stream()
                          .limit(limit)
                          .flatMap(entry -> entry.getValue().stream())
                          .collect(Collectors.toList());
        }
        return dataSet.entrySet()
                      .stream()
                      .limit(limit)
                      .flatMap(entry -> entry.getValue().stream())
                      .collect(Collectors.toList());
    }
}
