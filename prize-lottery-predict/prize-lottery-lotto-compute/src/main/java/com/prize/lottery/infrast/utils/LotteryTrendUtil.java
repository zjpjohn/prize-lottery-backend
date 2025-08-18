package com.prize.lottery.infrast.utils;

import com.alibaba.nacos.shaded.com.google.common.collect.Sets;
import com.google.common.base.Splitter;
import com.prize.lottery.utils.ICaiConstants;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LotteryTrendUtil {

    /**
     * 计算出号形态
     */
    public static Pair<Integer, String> calcForm(String lotto) {
        List<Integer> balls = Splitter.on(Pattern.compile("\\s+"))
                                      .trimResults()
                                      .splitToStream(lotto)
                                      .map(Integer::parseInt)
                                      .collect(Collectors.toList());
        Set<Integer> kuaList = calcKua(balls);
        if (kuaList.size() == 1) {
            return parse("豹子", ICaiConstants.FORM_CODE);
        }
        if (kuaList.size() == 2) {
            if (kuaList.contains(0)) {
                return parse("对子", ICaiConstants.FORM_CODE);
            }
            if (kuaList.contains(1)) {
                return parse("全顺", ICaiConstants.FORM_CODE);
            }
        }
        if (kuaList.contains(1)) {
            return parse("半顺", ICaiConstants.FORM_CODE);
        }
        return parse("杂六", ICaiConstants.FORM_CODE);
    }

    /**
     * 计算012形态
     */
    public static Pair<Integer, String> calcOtt(String lotto) {
        List<Integer> balls = Splitter.on(Pattern.compile("\\s+"))
                                      .trimResults()
                                      .splitToStream(lotto)
                                      .map(Integer::parseInt)
                                      .collect(Collectors.toList());
        Set<Integer> ottList = calc012(balls);
        if (ottList.size() == 3) {
            return parse("012", ICaiConstants.OTT_CODE);
        }
        if (ottList.size() == 1) {
            String key = ottList.contains(0) ? "00" : (ottList.contains(1) ? "11" : "22");
            return parse(key, ICaiConstants.OTT_CODE);
        }
        if (ottList.contains(0) && ottList.contains(1)) {
            return parse("01", ICaiConstants.OTT_CODE);
        }
        if (ottList.contains(0) && ottList.contains(2)) {
            return parse("02", ICaiConstants.OTT_CODE);
        }
        return parse("12", ICaiConstants.OTT_CODE);
    }

    /**
     * 计算升降造型形态
     */
    public static Pair<Integer, String> calcMode(String lotto) {
        List<Integer> balls = Splitter.on(Pattern.compile("\\s+"))
                                      .trimResults()
                                      .splitToStream(lotto)
                                      .map(Integer::parseInt)
                                      .collect(Collectors.toList());
        Integer bai = balls.get(0);
        Integer shi = balls.get(1);
        Integer ge  = balls.get(2);
        if (bai.equals(shi) && bai.equals(ge)) {
            return parse("平行", ICaiConstants.MODE_CODE);
        }
        if (shi < bai && shi < ge) {
            return parse("凹下", ICaiConstants.MODE_CODE);
        }
        if (shi > bai && shi > ge) {
            return parse("凸上", ICaiConstants.MODE_CODE);
        }
        if (shi >= bai && ge > shi || shi > bai) {
            return parse("上升", ICaiConstants.MODE_CODE);
        }
        return parse("下降", ICaiConstants.MODE_CODE);
    }

    /**
     * 计算大小形态
     */
    public static Pair<Integer, String> calcBs(String lotto) {
        long count = Splitter.on(Pattern.compile("\\s+"))
                             .trimResults()
                             .splitToStream(lotto)
                             .map(Integer::parseInt)
                             .filter(e -> e >= 5)
                             .count();
        if (count == 0) {
            return parse("全小", ICaiConstants.BS_CODE);
        }
        if (count == 1) {
            return parse("两小", ICaiConstants.BS_CODE);
        }
        if (count == 2) {
            return parse("两大", ICaiConstants.BS_CODE);
        }
        return parse("全大", ICaiConstants.BS_CODE);
    }

    public static String duiMa(String lottery) {
        if (lottery.contains("0") && lottery.contains("5")) {
            return "05";
        }
        if (lottery.contains("1") && lottery.contains("6")) {
            return "16";
        }
        if (lottery.contains("2") && lottery.contains("7")) {
            return "27";
        }
        if (lottery.contains("3") && lottery.contains("8")) {
            return "38";
        }
        if (lottery.contains("4") && lottery.contains("9")) {
            return "49";
        }
        return "";
    }

    public static List<String> duiMaList(String lottery) {
        return Splitter.on(Pattern.compile("\\s+"))
                       .trimResults()
                       .splitToStream(lottery)
                       .map(Integer::parseInt)
                       .map(e -> {
                           Integer[] duiMa = duiMa(e);
                           return Arrays.stream(duiMa).map(String::valueOf).collect(Collectors.joining(""));
                       })
                       .distinct()
                       .collect(Collectors.toList());

    }

    public static Integer[] duiMa(int value) {
        if (value < 5) {
            return new Integer[]{value, value + 5};
        }
        return new Integer[]{value - 5, value};
    }

    /**
     * 计算奇偶形态
     */
    public static Pair<Integer, String> calcOe(String lotto) {
        Map<Integer, Long> counts = Splitter.on(Pattern.compile("\\s+"))
                                            .trimResults()
                                            .splitToStream(lotto)
                                            .map(Integer::parseInt)
                                            .map(e -> e % 2)
                                            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        long even = Optional.ofNullable(counts.get(0)).orElse(0L);
        if (even == 0) {
            return parse("全奇", ICaiConstants.OE_CODE);
        }
        if (even == 1) {
            return parse("两奇", ICaiConstants.OE_CODE);
        }
        if (even == 2) {
            return parse("两偶", ICaiConstants.OE_CODE);
        }
        return parse("全偶", ICaiConstants.OE_CODE);
    }

    private static Pair<Integer, String> parse(String key, String[] codes) {
        for (int i = 1; i <= codes.length; i++) {
            String code = codes[i - 1];
            if (key.equals(code)) {
                return Pair.of(i, code);
            }
        }
        throw new IllegalArgumentException(String.format("无效形态key[%s]", key));
    }

    private static int calcKua(int ball1, int ball2) {
        return Math.abs(ball1 - ball2);
    }

    private static Set<Integer> calcKua(List<Integer> balls) {
        int kua1 = calcKua(balls.get(0), balls.get(1));
        int kua2 = calcKua(balls.get(0), balls.get(2));
        int kua3 = calcKua(balls.get(1), balls.get(2));
        return Sets.newHashSet(kua1, kua2, kua3);
    }

    private static Set<Integer> calc012(List<Integer> balls) {
        return balls.stream().map(e -> e % 3).collect(Collectors.toSet());
    }

    public static Integer calcAmp(List<Integer> last, List<Integer> current, int index) {
        return Math.abs(last.get(index) - current.get(index));
    }

    public static String calcBos(List<Integer> balls, int index) {
        return balls.get(index) >= 5 ? "大" : "小";
    }

    public static String calcOe(List<Integer> balls, int index) {
        return balls.get(index) % 2 == 0 ? "偶" : "奇";
    }

    public static String calcAod(List<Integer> last, List<Integer> current, int index) {
        int delta = current.get(index) - last.get(index);
        return delta > 0 ? "升" : (delta < 0 ? "降" : "平");
    }

    public static List<Integer> intBalls(String lottery) {
        return Splitter.on(Pattern.compile("\\s+"))
                       .trimResults()
                       .splitToStream(lottery)
                       .map(Integer::parseInt)
                       .collect(Collectors.toList());
    }

}
