package com.prize.lottery.utils;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class LotteryUtils {

    public static int duiMa(int value) {
        return value < 5 ? value + 5 : value - 5;
    }

    public static Integer sumTail(List<String> balls) {
        return balls.stream().mapToInt(Integer::parseInt).sum() % 10;
    }

    public static List<Integer> neighbors(int value) {
        List<Integer> result = Lists.newArrayList();
        result.add(value);
        result.add(value - 2 < 0 ? value - 2 + 10 : value - 2);
        result.add(value - 1 < 0 ? value - 1 + 10 : value - 1);
        result.add(value + 1 > 9 ? value + 1 - 10 : value + 1);
        result.add(value + 2 > 9 ? value + 2 - 10 : value + 2);
        return result;
    }

    public static List<String> unOpenLotto(List<String> opened) {
        List<String> result = Lists.newArrayList();
        for (int i = 0; i <= 9; i++) {
            for (int j = i; j <= 9; j++) {
                for (int k = j; k <= 9; k++) {
                    String lotto = i + " " + j + " " + k;
                    if (!opened.contains(lotto)) {
                        result.add(lotto);
                    }
                }
            }
        }
        Collections.sort(result);
        return result;
    }

}
