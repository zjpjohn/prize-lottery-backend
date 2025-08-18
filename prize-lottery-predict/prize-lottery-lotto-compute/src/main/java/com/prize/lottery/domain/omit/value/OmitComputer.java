package com.prize.lottery.domain.omit.value;

import com.google.common.base.Splitter;
import com.prize.lottery.value.Omit;
import com.prize.lottery.value.OmitValue;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class OmitComputer {

    /**
     * 计算和值
     */
    public static int getSum(List<String> balls) {
        return balls.stream().mapToInt(Integer::parseInt).sum();
    }

    /**
     * 计算号码跨度
     */
    public static int getKua(List<String> balls) {
        List<Integer> collects = balls.stream().sorted().map(Integer::parseInt).toList();
        Integer       min      = collects.get(0);
        Integer       max      = collects.get(collects.size() - 1);
        return max - min;
    }

    /**
     * 分割号码
     *
     * @param source 号码字符串
     */
    public static List<String> getBalls(String source) {
        String regex = source.contains(",") ? "," : "\\s+";
        return Splitter.on(Pattern.compile(regex)).omitEmptyStrings().trimResults().splitToList(source);
    }

    /**
     * 根据选三星上期开奖号遗漏计算本期开奖号遗漏
     *
     * @param omit  上期遗漏
     * @param balls 开奖号码
     */
    public static Omit nextN3Omit(Omit omit, List<String> balls) {
        List<OmitValue> values = omit.getValues().stream().map(o -> {
            Long count = count(balls, o.getKey());
            int  value = count >= 1 ? 0 : o.getValue() + 1;
            return new OmitValue(o.getKey(), value, count.toString());
        }).collect(Collectors.toList());
        return new Omit(values);
    }

    private static Long count(List<String> source, String key) {
        return source.stream().filter(key::equals).count();
    }

    /**
     * 根据上期开奖号遗漏计算本期开奖号遗漏
     *
     * @param omit  上期遗漏
     * @param balls 开奖号码
     */
    public static Omit nextOmit(Omit omit, List<String> balls) {
        List<OmitValue> values = omit.getValues().stream().map(o -> {
            int value = balls.contains(o.getKey()) ? 0 : o.getValue() + 1;
            return OmitValue.of(o.getKey(), value);
        }).collect(Collectors.toList());
        return new Omit(values);
    }

    /**
     * 单值遗漏计算
     *
     * @param omit 上期遗漏值
     * @param key  本期待计算遗漏值
     */
    public static Omit nextOmit(Omit omit, String key) {
        List<OmitValue> values = omit.getValues().stream().map(o -> {
            int value = key.equals(o.getKey()) ? 0 : o.getValue() + 1;
            return OmitValue.of(o.getKey(), value);
        }).collect(Collectors.toList());
        return new Omit(values);
    }

}
