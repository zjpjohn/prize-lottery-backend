package com.prize.lottery.infrast.combine;

import com.google.common.collect.Lists;

import java.util.List;

public class Combination {


    /**
     * 获取组合数中对应的数据
     *
     * @param source 数据源
     * @param m      组合m
     * @param x      组合数中位置
     */
    public static <T> List<T> value(List<T> source, int m, int x) {
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
                int cmb = (int) combine(n - 1 - i, m - 1);
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

    /**
     * 组合数计算
     *
     * @param n 底数N
     * @param m 选择数M
     */
    public static long combine(Integer n, Integer m) {
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

}
