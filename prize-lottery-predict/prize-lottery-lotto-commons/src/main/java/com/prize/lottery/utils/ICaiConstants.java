package com.prize.lottery.utils;

import com.google.common.collect.Lists;
import com.prize.lottery.value.RankValue;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ICaiConstants {

    public static final List<String> FC3D_BALLS     = Lists.newArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    public static final List<String> PL3_BALLS      = Lists.newArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    public static final List<String> DLT_RED_BALLS  = Lists.newArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35");
    public static final List<String> QLC_BALLS      = Lists.newArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30");
    public static final List<String> DLT_BLUE_BALLS = Lists.newArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
    public static final List<String> SSQ_RED_BALLS  = Lists.newArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33");
    public static final List<String> SSQ_BLUE_BALLS = Lists.newArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16");

    public static final List<String> KL8_BALLS = Lists.newArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80");

    //万能4码
    public static final String[] FOUR_CODE = new String[]{
            "0126",
            "0134",
            "0159",
            "0178",
            "0239",
            "0247",
            "0258",
            "0357",
            "0368",
            "0456",
            "0489",
            "0679",
            "1237",
            "1245",
            "1289",
            "1358",
            "1369",
            "1468",
            "1479",
            "1567",
            "2348",
            "2356",
            "2469",
            "2579",
            "2678",
            "3459",
            "3467",
            "3789",
            "4578",
            "5689"
    };
    //万能5码
    public static final String[] FIVE_CODE = new String[]{
            "01249",
            "01268",
            "01346",
            "01467",
            "01569",
            "02357",
            "02458",
            "03789",
            "12359",
            "12378",
            "12589",
            "13478",
            "14579",
            "23456",
            "24679",
            "34689",
            "35678"
    };

    public static Map<String, Integer> judgeLottery(List<String> lottery) {
        return lottery.stream()
                      .collect(Collectors.groupingBy(Function.identity(), Collectors.reducing(0, e -> 1, Integer::sum)));
    }

    public static void unionPadding(Map<String, Long> data, List<String> keys) {
        keys.stream().filter(v -> !data.containsKey(v)).forEach(v -> data.put(v, 0L));
    }

    public static void sortRank(List<RankValue> ranks) {
        AtomicInteger index = new AtomicInteger(1);
        ranks.stream().sorted(Comparator.reverseOrder()).forEach(rank -> rank.setRank(index.getAndIncrement()));
    }

    //号码形态常量
    public static String[] FORM_CODE = new String[]{"全顺", "半顺", "杂六", "对子", "豹子"};
    //012路常量
    public static String[] OTT_CODE  = new String[]{"00", "01", "02", "012", "11", "12", "22"};
    //升降造型常量
    public static String[] MODE_CODE = new String[]{"凸上", "凹下", "上升", "下降", "平行"};
    //大小形态常量
    public static String[] BS_CODE   = new String[]{"全小", "两小", "两大", "全大"};
    //奇偶形态常量
    public static String[] OE_CODE   = new String[]{"全奇", "两奇", "两偶", "全偶"};

}
