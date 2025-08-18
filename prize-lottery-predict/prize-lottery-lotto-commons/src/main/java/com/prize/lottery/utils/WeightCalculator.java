package com.prize.lottery.utils;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class WeightCalculator {

    private static final double SEED = 1.0;

    private static final double ICR_HIGH = 9.0;

    private static final double ICR_MIDDLE = 3.0;

    private static final double ICR_LOW = 1.0;

    private static final double OLD_WEIGHT = 0.38;

    private static final double NEW_WEIGHT = 0.62;


    /**
     * 计算权重
     *
     * @param oldValue
     * @param newValue
     * @return
     */
    public static double calcWeight(double oldValue, Integer newValue) {
        double weight;
        if (newValue == 3) {
            weight = OLD_WEIGHT * oldValue + NEW_WEIGHT * ICR_HIGH;
        } else if (newValue == 2) {
            weight = OLD_WEIGHT * oldValue + NEW_WEIGHT * ICR_MIDDLE;
        } else if (newValue == 1) {
            weight = OLD_WEIGHT * oldValue + NEW_WEIGHT * ICR_LOW;
        } else {
            weight = OLD_WEIGHT * oldValue;
        }
        BigDecimal rank = new BigDecimal(weight).setScale(2, RoundingMode.HALF_UP);
        return rank.doubleValue();
    }

    /**
     * 批量计算权重
     *
     * @param value
     * @return
     */
    public static double compute(double initWeight, Integer value) {
        double init;
        if (initWeight <= 1.0) {
            init = SEED;
        } else {
            init = initWeight;
        }
        return new BigDecimal(calcWeight(init, value)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 计算权重
     *
     * @param pairs
     * @return
     */
    public static Map<Integer, Double> compute(Map<Integer, Pair<Double, Integer>> pairs) {

        Map<Integer, Double> weights = Maps.newHashMap();
        for (Map.Entry<Integer, Pair<Double, Integer>> entry : pairs.entrySet()) {
            Pair<Double, Integer> pair   = entry.getValue();
            double                weight = compute(pair.getKey(), pair.getValue());
            weights.put(entry.getKey(), weight);
        }
        return weights;
    }
}
