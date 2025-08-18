package com.prize.lottery.utils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DanMaUtils {

    public static final Integer[][] weekDanTable =
        new Integer[][] {{7, 9, 0, 6}, {5, 9, 6, 8}, {5, 0, 4, 8}, {3, 6, 5, 0}, {3, 7, 4, 6}, {3, 5, 2, 6},
            {1, 5, 2, 4},};

    /**
     * 根据日期获取胆码
     */
    public static List<String> dateDan(LocalDate date) {
        int       value = date.getDayOfWeek().getValue();
        Integer[] array = weekDanTable[value];
        return Arrays.stream(array).map(String::valueOf).collect(Collectors.toList());
    }

    /**
     * 获取对码三胆
     *
     * @param lottery 开奖号
     * @param mode    模式
     */
    public static List<String> duiMaDan(String lottery, String mode) {
        List<Integer> balls = Splitter.on("\\s+")
                                      .trimResults()
                                      .splitToStream(lottery)
                                      .map(Integer::parseInt)
                                      .sorted()
                                      .distinct()
                                      .collect(Collectors.toList());
        return Mode.of(mode).map(e -> e.danList(balls)).orElseGet(Lists::newArrayList);
    }

    /**
     * 计算号码的对码胆
     */
    public static List<String> duiMaDan(int value) {
        int           duiMa  = value >= 5 ? value - 5 : value + 5;
        List<Integer> result = Lists.newArrayList();
        result.add(duiMa);
        int plus = duiMa + 1, minus = duiMa - 1;
        result.add(plus > 9 ? 0 : plus);
        result.add(minus < 0 ? 9 : minus);
        return result.stream().map(String::valueOf).collect(Collectors.toList());
    }

    @Getter
    @AllArgsConstructor
    public enum Mode {

        BIG("big", "大号计算") {
            @Override
            public List<String> danList(List<Integer> balls) {
                Integer max = balls.get(balls.size() - 1);
                return duiMaDan(max);
            }
        },
        MIDDLE("middle", "中号计算") {
            @Override
            public List<String> danList(List<Integer> balls) {
                int index = balls.size() == 3 ? 1 : 0;
                return duiMaDan(balls.get(index));
            }
        },
        SMALL("small", "小号计算") {
            @Override
            public List<String> danList(List<Integer> balls) {
                Integer min = balls.get(0);
                return duiMaDan(min);
            }
        };

        private final String mode;
        private final String remark;

        public abstract List<String> danList(List<Integer> balls);

        public static Optional<Mode> of(String mode) {
            return Arrays.stream(values()).filter(e -> e.mode.equals(mode)).findFirst();
        }

    }

}
