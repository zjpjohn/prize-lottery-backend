package com.prize.lottery.utils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Num3ZuHeUtils {

    /**
     * 根据奖号计算两码组合
     */
    public static List<String> twoMaList(String lottery) {
        List<Integer> balls = Splitter.on(Pattern.compile("\\s+"))
                                      .trimResults()
                                      .splitToStream(lottery)
                                      .map(Integer::parseInt)
                                      .distinct()
                                      .collect(Collectors.toList());
        List<List<Integer>> duiMa = calcDuiMa(balls);
        if (duiMa.size() > 2) {
            //使用最大的对码组合
            duiMa.sort((e1, e2) -> e2.get(0) - e1.get(0));
            duiMa = duiMa.subList(0, 2);
        }
        List<List<Integer>> danList  = danMaZu(duiMa);
        List<List<Integer>> zuHeList = lotteryZuHe(danList);
        return zuHeList.stream()
                       .map(e -> e.stream().map(String::valueOf).collect(Collectors.joining(" ")))
                       .collect(Collectors.toList());
    }

    private static List<List<Integer>> lotteryZuHe(List<List<Integer>> danMaZu) {
        Map<Integer, List<Integer>> group  = oddEvenGroup(danMaZu);
        List<List<Integer>>         result = Lists.newArrayList();
        result.addAll(calcZuHe(group.get(0), Lists.newArrayList(0, 2, 4, 6, 8)));
        result.addAll(calcZuHe(group.get(1), Lists.newArrayList(1, 3, 5, 7, 9)));
        return result;
    }

    private static List<List<Integer>> calcZuHe(List<Integer> values, List<Integer> all) {
        Collections.sort(values);
        List<Integer>       remain = all.stream().filter(e -> !values.contains(e)).collect(Collectors.toList());
        List<List<Integer>> result = Lists.newArrayList();
        result.add(values);
        for (int i = 0; i < remain.size(); i++) {
            for (int j = 0; j < values.size(); j++) {
                List<Integer> zuHe = Lists.newArrayList(remain.get(i), values.get(j));
                Collections.sort(zuHe);
                result.add(zuHe);
            }
        }
        return result;
    }

    private static Map<Integer, List<Integer>> oddEvenGroup(List<List<Integer>> danZu) {
        List<Integer> odd  = Lists.newArrayList();
        List<Integer> even = Lists.newArrayList();
        for (List<Integer> element : danZu) {
            Integer e0 = element.get(0);
            Integer e1 = element.get(1);
            if (e0 % 2 == 1) {
                odd.add(e0);
            } else {
                even.add(e0);
            }
            if (e1 % 2 == 1) {
                odd.add(e1);
            } else {
                even.add(e1);
            }
        }
        Map<Integer, List<Integer>> result = Maps.newHashMap();
        result.put(0, even);
        result.put(1, odd);
        return result;
    }

    private static List<List<Integer>> danMaZu(List<List<Integer>> duiMaList) {
        List<List<Integer>> result = Lists.newArrayList();
        result.add(duiMaZu(duiMaSumTail(duiMaList.get(0))));
        result.add(duiMaZu(duiMaSumTail(duiMaList.get(1))));
        return result;
    }

    private static Integer duiMaSumTail(List<Integer> duiMa) {
        return (duiMa.get(0) + duiMa.get(1)) % 10;
    }

    private static List<List<Integer>> calcDuiMa(List<Integer> balls) {
        List<List<Integer>> result = Lists.newArrayList();
        if (balls.size() == 1) {
            Integer ball = balls.get(0);
            result.add(duiMaZu(ball));
            result.add(duiMaZu(ball == 5 || ball == 0 ? 1 : 0));
            return result;
        }
        if (balls.size() == 2) {
            Integer ball0 = balls.get(0);
            Integer ball1 = balls.get(1);
            if (Math.abs(ball0 - ball1) == 5) {
                if (ball0 + ball1 != 5) {
                    result.add(duiMaZu(ball0));
                    result.add(duiMaZu(ball1));
                } else {
                    result.add(Lists.newArrayList(0, 5));
                    result.add(Lists.newArrayList(1, 6));
                }
            } else {
                result.add(duiMaZu(ball0));
                result.add(duiMaZu(ball1));
            }
            return result;
        }
        balls = duiMaFilter(balls);
        if (balls.size() == 2) {
            result.add(duiMaZu(balls.get(0)));
            result.add(duiMaZu(balls.get(1)));
            return result;
        }
        result.add(duiMaZu(balls.get(0)));
        result.add(duiMaZu(balls.get(1)));
        result.add(duiMaZu(balls.get(2)));
        return result;
    }

    private static List<Integer> duiMaFilter(List<Integer> balls) {
        if (Math.asin(balls.get(0) - balls.get(1)) == 5) {
            return Lists.newArrayList(balls.get(0), balls.get(2));
        }
        if (Math.abs(balls.get(0) - balls.get(2)) == 5 || Math.asin(balls.get(1) - balls.get(2)) == 5) {
            return Lists.newArrayList(balls.get(0), balls.get(1));
        }
        return balls;
    }

    private static List<Integer> duiMaZu(int ball) {
        if (ball < 5) {
            return Lists.newArrayList(ball, ball + 5);
        }
        return Lists.newArrayList(ball - 5, ball);
    }

}
