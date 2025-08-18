package com.prize.lottery.infrast.utils;

import com.cloud.arch.utils.CollectionUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.prize.lottery.infrast.commons.WensFilterResult;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class WensFilterCalculator {

    //稳氏四段
    static final int[][]      segment1 = {{1, 3, 8}, {4, 9}, {5, 7}, {0, 2, 6}};
    static final int[][]      segment2 = {{1, 6, 7}, {3, 8}, {5, 9}, {0, 2, 4}};
    static final int[][]      segment3 = {{2, 7, 9}, {1, 6}, {3, 5}, {0, 4, 8}};
    static final int[][]      segment4 = {{3, 4, 9}, {2, 7}, {1, 5}, {0, 6, 8}};
    //4胆码表
    static final Set<Integer> dan1     = Sets.newHashSet(1, 3, 6, 8);
    static final Set<Integer> dan2     = Sets.newHashSet(1, 2, 6, 7);
    static final Set<Integer> dan3     = Sets.newHashSet(2, 4, 7, 9);
    static final Set<Integer> dan4     = Sets.newHashSet(3, 4, 8, 9);
    static final Set<Integer> dan5     = Sets.newHashSet(2, 5, 7, 9);

    /**
     * 计算四段式
     */
    private static Pair<Integer, int[][]> calcPattern(List<Integer> balls) {
        List<Integer> collected = balls.stream().distinct().collect(Collectors.toList());
        //豹子计算
        if (collected.size() == 1) {
            int[]       neighbor     = judgeNeighbor(collected.get(0));
            int[]       index1       = calcIndex(neighbor[0]);
            int         index1Tail   = indexSumTail(index1);
            int[]       index2       = calcIndex(neighbor[1]);
            int         index2Tail   = indexSumTail(index2);
            List<int[]> remainIndex  = calcRemainIndex(Lists.newArrayList(index1[0], index2[0]));
            int[]       index3       = remainIndex.get(0);
            int         index3Tail   = indexSumTail(index3);
            int[]       index4       = remainIndex.get(1);
            int         index4Tail   = indexSumTail(index4);
            int[]       index5       = remainIndex.get(2);
            int         index5Tail   = indexSumTail(index5);
            int         remainedTail = remainTail(index1Tail, index2Tail, index3Tail, index4Tail, index5Tail);
            return negotiatePattern(remainedTail);
        }
        //组三计算
        if (collected.size() == 2) {
            if (Math.abs(collected.get(0) - collected.get(1)) == 5) {
                if (collected.get(0) + collected.get(1) != 5) {
                    int[]       index1       = calcIndex(collected.get(0));
                    int         index1Tail   = indexSumTail(index1);
                    int[]       index2       = new int[] {0, 5};
                    int         index2Tail   = indexSumTail(index2);
                    List<int[]> remainIndex  = calcRemainIndex(Lists.newArrayList(index1[0], index2[0]));
                    int[]       index3       = remainIndex.get(0);
                    int         index3Tail   = indexSumTail(index3);
                    int[]       index4       = remainIndex.get(1);
                    int         index4Tail   = indexSumTail(index4);
                    int[]       index5       = remainIndex.get(2);
                    int         index5Tail   = indexSumTail(index5);
                    int         remainedTail = remainTail(index4Tail, index5Tail, index1Tail, index2Tail, index3Tail);
                    return negotiatePattern(remainedTail);
                }
                int[]       index1       = new int[] {0, 5};
                int         index1Tail   = indexSumTail(index1);
                int[]       index2       = new int[] {1, 6};
                int         index2Tail   = indexSumTail(index2);
                List<int[]> remainIndex  = calcRemainIndex(Lists.newArrayList(index1[0], index2[0]));
                int[]       index3       = remainIndex.get(0);
                int         index3Tail   = indexSumTail(index3);
                int[]       index4       = remainIndex.get(1);
                int         index4Tail   = indexSumTail(index4);
                int[]       index5       = remainIndex.get(2);
                int         index5Tail   = indexSumTail(index5);
                int         remainedTail = remainTail(index4Tail, index5Tail, index1Tail, index2Tail, index3Tail);
                return negotiatePattern(remainedTail);
            }
            int[]       index1       = calcIndex(collected.get(0));
            int         index1Tail   = indexSumTail(index1);
            int[]       index2       = calcIndex(collected.get(1));
            int         index2Tail   = indexSumTail(index2);
            List<int[]> remainIndex  = calcRemainIndex(Lists.newArrayList(index1[0], index2[0]));
            int[]       index3       = remainIndex.get(0);
            int         index3Tail   = indexSumTail(index3);
            int[]       index4       = remainIndex.get(1);
            int         index4Tail   = indexSumTail(index4);
            int[]       index5       = remainIndex.get(2);
            int         index5Tail   = indexSumTail(index5);
            int         remainedTail = remainTail(index1Tail, index2Tail, index3Tail, index4Tail, index5Tail);
            return negotiatePattern(remainedTail);
        }
        //组六计算
        int[] index1     = calcIndex(collected.get(0));
        int   index1Tail = indexSumTail(index1);
        int[] index2     = calcIndex(collected.get(1));
        int   index2Tail = indexSumTail(index2);
        int[] index3     = calcIndex(collected.get(2));
        int   index3Tail = indexSumTail(index3);
        if (index1Tail != index2Tail && index2Tail != index3Tail && index1Tail != index3Tail) {
            //和尾三不同
            List<int[]> remainIndex  = calcRemainIndex(Lists.newArrayList(index1[0], index2[0], index3[0]));
            int[]       index4       = remainIndex.get(0);
            int         index4Tail   = indexSumTail(index4);
            int[]       index5       = remainIndex.get(1);
            int         index5Tail   = indexSumTail(index5);
            int         remainedTail = remainTail(index4Tail, index5Tail, index1Tail, index2Tail, index3Tail);
            return negotiatePattern(remainedTail);
        }
        if (index1Tail == index2Tail) {
            //和尾12相同
            List<int[]> remainIndex  = calcRemainIndex(Lists.newArrayList(index1[0], index3[0]));
            int[]       index4       = remainIndex.get(0);
            int         index4Tail   = indexSumTail(index4);
            int[]       index5       = remainIndex.get(1);
            int         index5Tail   = indexSumTail(index5);
            int[]       index6       = remainIndex.get(2);
            int         index6Tail   = indexSumTail(index6);
            int         remainedTail = remainTail(index1Tail, index3Tail, index4Tail, index5Tail, index6Tail);
            return negotiatePattern(remainedTail);
        }
        //和尾13相同或23相同
        List<int[]> remainIndex  = calcRemainIndex(Lists.newArrayList(index1[0], index2[0]));
        int[]       index4       = remainIndex.get(0);
        int         index4Tail   = indexSumTail(index4);
        int[]       index5       = remainIndex.get(1);
        int         index5Tail   = indexSumTail(index5);
        int[]       index6       = remainIndex.get(2);
        int         index6Tail   = indexSumTail(index6);
        int         remainedTail = remainTail(index1Tail, index2Tail, index4Tail, index5Tail, index6Tail);
        return negotiatePattern(remainedTail);
    }

    private static Pair<Integer, int[][]> negotiatePattern(int tail) {
        if (tail == 5) {
            return Pair.of(5, segment3);
        }
        if (judgePattern(tail, segment1[0])) {
            return Pair.of(1, segment1);
        }
        if (judgePattern(tail, segment2[0])) {
            return Pair.of(2, segment2);
        }
        if (judgePattern(tail, segment3[0])) {
            return Pair.of(3, segment3);
        }
        return Pair.of(4, segment4);
    }

    private static int[] judgeNeighbor(int value) {
        if (value == 0) {
            return new int[] {1, 9};
        }
        if (value == 9) {
            return new int[] {0, 8};
        }
        return new int[] {value - 1, value + 1};
    }

    private static boolean judgePattern(int tail, int[] indices) {
        return (Math.abs(indices[0] - indices[1]) == 5 && tail == (indices[0] + indices[1]) % 10)
               || (Math.abs(indices[0] - indices[2]) == 5 && tail == (indices[0] + indices[2]) % 10)
               || (Math.abs(indices[1] - indices[2]) == 5 && tail == (indices[1] + indices[2]) % 10);
    }

    private static int remainTail(int index1, int index2, int index3, int index4, int index5) {
        int index12 = (index1 + index2) % 10;
        if (index12 == (index3 + index4) % 10) {
            return index5;
        }
        if (index12 == (index3 + index5) % 10) {
            return index4;
        }
        return index3;
    }

    private static int[] calcIndex(int value) {
        int[] index = new int[2];
        if (value < 5) {
            index[0] = value;
            index[1] = value + 5;
        } else {
            index[0] = value - 5;
            index[1] = value;
        }
        return index;
    }

    private static List<int[]> calcRemainIndex(List<Integer> indices) {
        return IntStream.range(0, 5)
                        .filter(i -> !indices.contains(i))
                        .mapToObj(WensFilterCalculator::calcIndex)
                        .collect(Collectors.toList());
    }

    private static int indexSumTail(int[] index) {
        return (index[0] + index[1]) % 10;
    }

    private static Set<Integer> judgeDanTable(int pattern) {
        switch (pattern) {
            case 1:
                return dan1;
            case 2:
                return dan2;
            case 3:
                return dan3;
            case 4:
                return dan4;
            default:
                return dan5;
        }
    }

    private static boolean danTableFilter(int i, int j, int k, Set<Integer> danTable) {
        return danTable.contains(i) || danTable.contains(j) || danTable.contains(k);
    }

    private static boolean kuaFilter(int i, int j, int k, List<Integer> kuaList) {
        if (CollectionUtils.isEmpty(kuaList)) {
            return true;
        }
        int max = Math.max(i, Math.max(j, k));
        int min = Math.min(i, Math.min(j, k));
        return kuaList.contains(max - min);
    }

    private static boolean danFilter(int i, int j, int k, Integer dan) {
        if (dan == null) {
            return true;
        }
        return dan.equals(i) || dan.equals(j) || dan.equals(k);
    }

    private static boolean killFilter(int i, int j, int k, List<Integer> kills) {
        if (CollectionUtils.isEmpty(kills)) {
            return false;
        }
        return kills.contains(i) || kills.contains(j) || kills.contains(k);
    }

    private static boolean sumFilter(int i, int j, int k, Range<Integer> range) {
        if (range == null) {
            return true;
        }
        return range.contains(i + j + k);
    }

    /**
     * 找出对应胆码表
     */
    public static List<String> danTable(List<String> balls) {
        List<Integer> lotto = balls.stream().map(Integer::parseInt).collect(Collectors.toList());
        //计算四段组合信息
        Pair<Integer, int[][]> pattern = calcPattern(lotto);
        //返回4胆码表
        return judgeDanTable(pattern.getKey()).stream().map(String::valueOf).collect(Collectors.toList());
    }

    /**
     * 根据本期号码计算下一期开奖号
     */
    public static WensFilterResult filter(List<String> balls, Integer dan, List<Integer> kills, List<Integer> kuaList,
        Range<Integer> sumRange) {
        List<Integer>    lotto  = balls.stream().map(Integer::parseInt).collect(Collectors.toList());
        WensFilterResult result = new WensFilterResult();
        //计算四段组合信息
        Pair<Integer, int[][]> pattern = calcPattern(lotto);
        result.buildSegment(pattern.getValue());
        //获取胆码表
        Set<Integer> danTable = judgeDanTable(pattern.getKey());
        result.buildDanTable(danTable);
        Set<String> filtered = Sets.newHashSet();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    if (!danTableFilter(i, j, k, danTable)
                        || !danFilter(i, j, k, dan)
                        || !kuaFilter(i, j, k, kuaList)
                        || killFilter(i, j, k, kills)
                        || !sumFilter(i, j, k, sumRange)) {
                        continue;
                    }
                    String joined = Arrays.stream(new int[] {i, j, k})
                                          .mapToObj(String::valueOf)
                                          .sorted()
                                          .collect(Collectors.joining(""));
                    filtered.add(joined);
                }
            }
        }
        List<WensFilterResult.ResultItem> resultItems =
            filtered.stream().map(WensFilterResult.ResultItem::new).collect(Collectors.toList());
        result.setValues(resultItems);
        return result;
    }

}
