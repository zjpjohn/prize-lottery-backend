package com.prize.lottery.infrast.utils;

import com.google.common.collect.Lists;
import com.prize.lottery.infrast.combine.Combination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class LottoAnalyzeUtil {

    /**
     * 胆码过滤
     */
    public static boolean isContainDans(List<String> sources, List<String> dans) {
        if (CollectionUtils.isEmpty(dans) || CollectionUtils.isEmpty(sources)) {
            return false;
        }
        List<String> target = Lists.newArrayList(sources);
        target.retainAll(dans);
        return !target.isEmpty();
    }

    /**
     * 跨度过滤
     */
    public static boolean filterKua(List<String> sources, List<Integer> kuas) {
        if (CollectionUtils.isEmpty(kuas)) {
            return true;
        }
        int kua = Integer.parseInt(sources.get(sources.size() - 1)) - Integer.parseInt(sources.get(0));
        return kuas.contains(kua);
    }

    /**
     * 和值过滤
     */
    public static boolean filterSum(List<String> balls) {
        if (CollectionUtils.isEmpty(balls) || balls.size() == 1) {
            return false;
        }
        List<Integer> intBalls = balls.stream().map(Integer::parseInt).collect(Collectors.toList());
        Integer       sum      = intBalls.stream().reduce(0, Integer::sum);
        if (intBalls.size() == 2) {
            //对子和值只要有一个小于23的保留
            return (sum + intBalls.get(0) < 23) || (sum + intBalls.get(1)) < 23;
        }
        return sum < 23;
    }

    /**
     * 和值过滤
     */
    public static boolean filterSum(List<String> balls, List<Integer> sumList) {
        if (CollectionUtils.isEmpty(sumList)) {
            return true;
        }
        if (CollectionUtils.isEmpty(balls) || balls.size() == 1) {
            return false;
        }
        Integer sum = balls.stream().mapToInt(Integer::parseInt).sum();
        return sumList.contains(sum);
    }

    /**
     * 选三码组合推荐分析
     *
     * @param datas 数据源
     * @param dans  胆码集合
     * @param zu    组选类型:2-组三,3-组六
     * @param start 出现次数start范围
     * @param end   出现次数end范围
     */
    public static Set<String> n3ComAnalyze(List<List<String>> datas,
                                           List<String> dans,
                                           List<Integer> kuaList,
                                           int zu,
                                           int start,
                                           int end) {
        List<String> values = Lists.newArrayList();
        for (List<String> list : datas) {
            long combine = Combination.combine(list.size(), zu);
            for (int j = 0; j < combine; j++) {
                List<String> value1 = Combination.value(list, zu, j);
                if (isContainDans(value1, dans) && filterKua(value1, kuaList) && filterSum(value1)) {
                    String lotto = value1.stream().sorted().collect(Collectors.joining(" "));
                    values.add(lotto);
                }
            }
        }
        return values.stream()
                     .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                     .entrySet()
                     .stream()
                     .filter(entry -> entry.getValue() >= start && entry.getValue() <= end)
                     .sorted((o1, o2) -> (int) (o2.getValue() - o1.getValue()))
                     .map(Map.Entry::getKey)
                     .collect(Collectors.toSet());
    }

}
