package com.prize.lottery.domain.omit.value;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.stream.Collectors;

public class Kl8Rules {

    public static RangeMap<Integer, String> sumRules = TreeRangeMap.create();
    public static RangeMap<Integer, String> kuaRule = TreeRangeMap.create();

    static {
        //和值规则
        sumRules.put(Range.closed(210, 402), "210+");
        sumRules.put(Range.closed(403, 558), "403+");
        sumRules.put(Range.closed(559, 654), "559+");
        sumRules.put(Range.closed(655, 714), "655+");
        sumRules.put(Range.closed(715, 750), "715+");
        sumRules.put(Range.closed(751, 774), "751+");
        sumRules.put(Range.closed(775, 798), "775+");
        sumRules.put(Range.closed(799, 822), "799+");
        sumRules.put(Range.closed(823, 846), "823+");
        sumRules.put(Range.closed(847, 870), "847+");
        sumRules.put(Range.closed(871, 906), "871+");
        sumRules.put(Range.closed(907, 966), "907+");
        sumRules.put(Range.closed(967, 1062), "967+");
        sumRules.put(Range.closed(1063, 1218), "1063+");
        sumRules.put(Range.closed(1219, 1410), "1219+");
        //跨度规则
        kuaRule.put(Range.closed(19, 38), "19+");
        kuaRule.put(Range.closed(39, 52), "39+");
        kuaRule.put(Range.closed(53, 61), "53+");
        kuaRule.put(Range.closed(62, 69), "62+");
        kuaRule.put(Range.closed(70, 70), "70");
        kuaRule.put(Range.closed(71, 71), "71");
        kuaRule.put(Range.closed(72, 72), "72");
        kuaRule.put(Range.closed(73, 73), "73");
        kuaRule.put(Range.closed(74, 74), "74");
        kuaRule.put(Range.closed(75, 75), "75");
        kuaRule.put(Range.closed(76, 76), "76");
        kuaRule.put(Range.closed(77, 77), "77");
        kuaRule.put(Range.closed(78, 78), "78");
        kuaRule.put(Range.closed(79, 79), "79");
    }

    /**
     * 计算和值所属范围
     */
    public static Pair<Integer, String> calcSum(List<String> balls) {
        Integer sum = balls.stream().mapToInt(Integer::valueOf).sum();
        return Pair.of(sum, sumRules.get(sum));
    }

    /**
     * 计算跨度所属范围
     */
    public static Pair<Integer, String> calcKua(List<String> balls) {
        List<Integer> intBalls = balls.stream().map(Integer::parseInt).sorted().collect(Collectors.toList());
        int           kua      = intBalls.get(intBalls.size() - 1) - intBalls.get(0);
        return Pair.of(kua, kuaRule.get(kua));
    }

    /**
     * 计算大小比
     */
    public static String calcBsRatio(List<String> balls) {
        long smalls = balls.stream().map(Integer::parseInt).filter(ball -> ball <= 40).count();
        long bigs   = 20 - smalls;
        return bigs + ":" + smalls;
    }

    /**
     * 计算奇偶比
     */
    public static String calcOeRatio(List<String> balls) {
        long odds  = balls.stream().map(Integer::parseInt).filter(ball -> ball % 2 == 1).count();
        long evens = 20 - odds;
        return odds + ":" + evens;
    }

}
