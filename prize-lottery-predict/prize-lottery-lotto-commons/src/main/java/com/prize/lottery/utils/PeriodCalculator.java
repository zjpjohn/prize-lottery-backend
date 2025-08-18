package com.prize.lottery.utils;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.prize.lottery.enums.LotteryEnum;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@UtilityClass
public class PeriodCalculator {

    private static final String                        FC3D_MAX   = "351";
    private static final String                        DLT_MAX    = "151";
    private static final String                        PL3_MAX    = "351";
    private static final String                        SSQ_MAX    = "151";
    private static final String                        QLC_MAX    = "151";
    private static final Table<String, String, String> MAX_PERIOD = HashBasedTable.create();

    public static void loadMax(Table<String, String, String> table) {
        MAX_PERIOD.putAll(table);
    }

    public static String ofMax(LotteryEnum type, String year, String defaultValue) {
        return Optional.ofNullable(MAX_PERIOD.get(type.value(), year)).orElse(defaultValue);
    }

    public static int fc3dDelta(String target, String source) {
        return delta(LotteryEnum.FC3D, target, source, FC3D_MAX);
    }

    public static int pl3Delta(String target, String source) {
        return delta(LotteryEnum.PL3, target, source, PL3_MAX);
    }

    public static int ssqDelta(String target, String source) {
        return delta(LotteryEnum.SSQ, target, source, SSQ_MAX);
    }

    public static int dltDelta(String target, String source) {
        return delta(LotteryEnum.DLT, target, source, DLT_MAX);
    }

    public static int qlcDelta(String target, String source) {
        return delta(LotteryEnum.QLC, target, source, QLC_MAX);
    }

    /**
     * 计算3d开奖期号
     */
    public static String fc3dPeriod(String period, Integer delta) {
        return calcPeriod(period, delta, Integer.parseInt(FC3D_MAX), year -> ofMax(LotteryEnum.FC3D, year, FC3D_MAX));
    }

    public static List<String> fc3dPeriods(String period, Integer size) {
        return IntStream.range(0, size).mapToObj(delta -> fc3dPeriod(period, delta)).collect(Collectors.toList());
    }

    /**
     * 计算排列3期号
     */
    public static String pl3Period(String period, Integer delta) {
        return calcPeriod(period, delta, Integer.parseInt(PL3_MAX), year -> ofMax(LotteryEnum.PL3, year, PL3_MAX));
    }

    public static List<String> pl3Periods(String period, Integer size) {
        return IntStream.range(0, size).mapToObj(delta -> pl3Period(period, delta)).collect(Collectors.toList());
    }

    /**
     * 计算双色球期号
     */
    public static String ssqPeriod(String period, Integer delta) {
        return calcPeriod(period, delta, Integer.parseInt(SSQ_MAX), year -> ofMax(LotteryEnum.SSQ, year, SSQ_MAX));
    }

    public static List<String> ssqPeriods(String period, Integer size) {
        return IntStream.range(0, size).mapToObj(delta -> ssqPeriod(period, delta)).collect(Collectors.toList());
    }

    /**
     * 计算大乐透期号
     */
    public static String dltPeriod(String period, Integer delta) {
        return calcPeriod(period, delta, Integer.parseInt(DLT_MAX), year -> ofMax(LotteryEnum.DLT, year, DLT_MAX));
    }

    public static List<String> dltPeriods(String period, Integer size) {
        return IntStream.range(0, size).mapToObj(delta -> dltPeriod(period, delta)).collect(Collectors.toList());
    }

    /**
     * 计算七乐彩期号
     */
    public static String qlcPeriod(String period, Integer delta) {
        return calcPeriod(period, delta, Integer.parseInt(QLC_MAX), year -> ofMax(LotteryEnum.QLC, year, QLC_MAX));
    }

    public static List<String> qlcPeriods(String period, Integer size) {
        return IntStream.range(0, size).mapToObj(delta -> qlcPeriod(period, delta)).collect(Collectors.toList());
    }

    /**
     * 计算两期相差期数
     */
    public static int delta(LotteryEnum type, String target, String source, String defaultValue) {
        if (StringUtils.isBlank(target) || StringUtils.isBlank(source)) {
            return 0;
        }
        String big   = target;
        String small = source;
        if (target.compareTo(source) < 0) {
            big   = small;
            small = target;
        }
        int    bigYearInt   = Integer.parseInt(big.substring(0, 4));
        int    bigTail      = Integer.parseInt(big.substring(4));
        String smallYear    = small.substring(0, 4);
        int    smallYearInt = Integer.parseInt(smallYear);
        int    smallTail    = Integer.parseInt(small.substring(4));
        if (bigYearInt == smallYearInt) {
            return bigTail - smallTail;
        }
        if (bigYearInt - smallYearInt == 1) {
            return Integer.parseInt(ofMax(type, smallYear, defaultValue)) - smallTail + bigTail;
        }
        int yearDelta = bigYearInt - smallYearInt;
        int delta     = Integer.parseInt(ofMax(type, smallYear, defaultValue)) - smallTail;
        for (int i = 1; i < yearDelta; i++) {
            String year = String.valueOf(smallYearInt + 1);
            delta = delta + Integer.parseInt(ofMax(type, year, defaultValue));
        }
        delta = delta + bigTail;
        return delta;
    }

    /**
     * 减指定偏移量的期号
     */
    public static String calcPeriod(String period, Integer delta, Integer defaultMax, Function<String, String> max) {
        Assert.state(Math.abs(delta) <= defaultMax, "计算期号偏移量不允许大于'" + defaultMax + "'期.");
        int    possiblePeriod = Integer.parseInt(period) - delta;
        String year           = Integer.toString(possiblePeriod).substring(0, 4);
        String subTail        = Integer.toString(possiblePeriod).substring(4);
        if (subTail.equals("000")) {
            Integer lastYear  = Integer.parseInt(year) - 1;
            String  maxPeriod = max.apply(lastYear.toString());
            return lastYear + maxPeriod;
        }
        String possibleMax = max.apply(year);
        String maxPeriod   = year + possibleMax;
        if (possiblePeriod > Integer.parseInt(maxPeriod)) {
            if (delta < 0) {
                int lastDelta = Integer.parseInt(maxPeriod) - Integer.parseInt(period);
                int nextYear  = Integer.parseInt(period.substring(0, 4)) + 1;
                int start     = Integer.parseInt(nextYear + "001");
                return String.valueOf(start - delta - lastDelta - 1);
            }
            int start     = Integer.parseInt(period.substring(0, 4) + "001");
            int lastDelta = Integer.parseInt(period) - start;
            int lastMax   = Integer.parseInt(maxPeriod);
            return String.valueOf(lastMax - delta + lastDelta + 1);
        }
        return Integer.toString(possiblePeriod);
    }

    public static String fc3dAddPeriod(String period, Integer addition) {
        return calcPeriod(period, -1
                * addition, Integer.parseInt(FC3D_MAX), year -> ofMax(LotteryEnum.FC3D, year, FC3D_MAX));
    }

    public static String pl3AddPeriod(String period, Integer addition) {
        return calcPeriod(period, -1
                * addition, Integer.parseInt(PL3_MAX), year -> ofMax(LotteryEnum.PL3, year, PL3_MAX));
    }

    public static String ssqAddPeriod(String period, Integer addition) {
        return calcPeriod(period, -1
                * addition, Integer.parseInt(SSQ_MAX), year -> ofMax(LotteryEnum.SSQ, year, SSQ_MAX));
    }

    public static String dltAddPeriod(String period, Integer addition) {
        return calcPeriod(period, -1
                * addition, Integer.parseInt(DLT_MAX), year -> ofMax(LotteryEnum.DLT, year, DLT_MAX));
    }

    public static String qlcAddPeriod(String period, Integer addition) {
        return calcPeriod(period, -1
                * addition, Integer.parseInt(QLC_MAX), year -> ofMax(LotteryEnum.QLC, year, QLC_MAX));
    }

}
