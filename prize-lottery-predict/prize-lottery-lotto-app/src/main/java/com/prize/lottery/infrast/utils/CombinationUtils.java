package com.prize.lottery.infrast.utils;

import com.cloud.arch.utils.CollectionUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CombinationUtils {

    /**
     * 计算组合数对应的值
     */
    public static <T> List<T> getCombineValue(List<T> source, int m, int x) {
        int     n     = source.size();
        List<T> temp  = Lists.newArrayList();
        int     start = 0;
        while (m > 0) {
            if (m == 1) {
                //最后一个数
                temp.add(source.get(start + x));
                break;
            }
            for (int i = 0; i <= (n - m); i++) {
                int cmb = (int) getCombine(n - 1 - i, m - 1);
                if (x <= cmb - 1) {
                    temp.add(source.get(start + i));
                    start = start + (i + 1);
                    n     = n - (i + 1);
                    m--;
                    break;
                } else {
                    x = x - cmb;
                }
            }
        }
        return temp;
    }

    public static long getCombine(Integer n, Integer m) {
        if (n < 0 || m < 0) {
            throw new IllegalArgumentException("n,m必须大于0");
        }
        if (n == 0 || m == 0) {
            return 1;
        }
        if (n < m) {
            return 0;
        }
        if (m > n / 2.0) {
            m = n - m;
        }
        double result = 0.0;
        for (int i = n; i >= (n - m + 1); i--) {
            result += Math.log(i);
        }
        for (int i = m; i >= 1; i--) {
            result -= Math.log(i);
        }
        result = Math.exp(result);
        return Math.round(result);
    }

    /**
     * 求两个集合的差集
     */
    public static List<String> differences(List<String> source, List<String> target) {
        Sets.SetView<String> result = Sets.difference(Sets.newHashSet(source), Sets.newHashSet(target));
        return result.stream().sorted().collect(Collectors.toList());
    }

    /**
     * 求两个集合的交集
     */
    public static List<String> commonStr(List<String> source, List<String> target) {
        if (CollectionUtils.isEmpty(source) || CollectionUtils.isEmpty(target)) {
            return Collections.emptyList();
        }
        return source.stream().filter(target::contains).collect(Collectors.toList());
    }

}

