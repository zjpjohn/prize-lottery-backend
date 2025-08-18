package com.prize.lottery.infrast.utils;

import com.google.common.collect.Lists;
import com.prize.lottery.utils.ICaiConstants;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class LottoCodeUtil {

    /**
     * 计算万能4码位置
     */
    public static List<Pair<Integer, String>> fourParse(String lotto) {
        return parse(lotto, ICaiConstants.FOUR_CODE);
    }

    /**
     * 计算万能五码位置
     */
    public static List<Pair<Integer, String>> fiveParse(String lotto) {
        return parse(lotto, ICaiConstants.FIVE_CODE);
    }

    private static List<Pair<Integer, String>> parse(String lotto, String[] codes) {
        String[]                    splits = lotto.split("\\s+");
        int                         length = codes.length;
        List<Pair<Integer, String>> result = Lists.newArrayList();
        for (int index = 1; index <= length; index++) {
            String code = codes[index - 1];
            if (contains(splits, code)) {
                Pair<Integer, String> pair = Pair.of(index, code);
                result.add(pair);
            }
        }
        return result;
    }

    private static boolean contains(String[] lottos, String code) {
        boolean result = false;
        for (String lotto : lottos) {
            result = code.contains(lotto);
            if (!result) {
                break;
            }
        }
        return result;
    }

}
