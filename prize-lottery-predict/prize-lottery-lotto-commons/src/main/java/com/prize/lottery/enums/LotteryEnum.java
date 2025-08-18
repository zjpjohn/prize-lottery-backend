package com.prize.lottery.enums;

import com.cloud.arch.enums.Value;
import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.google.common.collect.Lists;
import com.prize.lottery.utils.PeriodCalculator;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@TypeHandler(type = Type.ENUM)
public enum LotteryEnum implements Value<String> {
    FC3D("fc3d", "福彩3D", (byte) 1, "c7") {
        @Override
        public boolean freeTime(LocalDateTime time) {
            int hour   = time.getHour();
            int minute = time.getMinute();
            return hour > 21 || (hour == 21 && minute >= 14);
        }

        @Override
        public boolean fastTable() {
            return true;
        }

        @Override
        public String lastPeriod(String period) {
            return PeriodCalculator.fc3dPeriod(period, 1);
        }

        @Override
        public String lastPeriod(String period, int delta) {
            return PeriodCalculator.fc3dPeriod(period, delta);
        }

        @Override
        public String nextPeriod(String period) {
            return PeriodCalculator.fc3dAddPeriod(period, 1);
        }

        @Override
        public String nextPeriod(String period, Integer delta) {
            return PeriodCalculator.fc3dAddPeriod(period, delta);
        }

        @Override
        public List<String> lastPeriods(String period, Integer size) {
            return PeriodCalculator.fc3dPeriods(period, size);
        }

        @Override
        public LotteryEnum parseLottery(String content) {
            return content.contains("福彩3d") || content.contains("福彩3D") || content.contains("福彩三D") ?
                   this :
                   null;
        }

        @Override
        public int delta(String target, String source) {
            return PeriodCalculator.fc3dDelta(target, source);
        }
    },
    PL3("pl3", "排列三", (byte) 2, "c7") {
        @Override
        public boolean freeTime(LocalDateTime time) {
            int hour   = time.getHour();
            int minute = time.getMinute();
            return hour > 21 || (hour == 21 && minute >= 24);
        }

        @Override
        public boolean fastTable() {
            return true;
        }

        @Override
        public String lastPeriod(String period) {
            return PeriodCalculator.pl3Period(period, 1);
        }

        @Override
        public String lastPeriod(String period, int delta) {
            return PeriodCalculator.pl3Period(period, delta);
        }

        @Override
        public String nextPeriod(String period) {
            return PeriodCalculator.pl3AddPeriod(period, 1);
        }

        @Override
        public String nextPeriod(String period, Integer delta) {
            return PeriodCalculator.pl3AddPeriod(period, delta);
        }

        @Override
        public List<String> lastPeriods(String period, Integer size) {
            return PeriodCalculator.pl3Periods(period, size);
        }

        @Override
        public LotteryEnum parseLottery(String content) {
            return content.contains("排列三") || content.contains("排列3") ? this : null;
        }

        @Override
        public int delta(String target, String source) {
            return PeriodCalculator.pl3Delta(target, source);
        }
    },
    PL5("pl5", "排列五", (byte) 64, "c7") {
        @Override
        public boolean freeTime(LocalDateTime time) {
            int hour   = time.getHour();
            int minute = time.getMinute();
            return hour > 21 || (hour == 21 && minute >= 10);
        }

        @Override
        public String lastPeriod(String period) {
            return PeriodCalculator.pl3Period(period, 1);
        }

        @Override
        public String lastPeriod(String period, int delta) {
            return PeriodCalculator.pl3Period(period, delta);
        }

        @Override
        public String nextPeriod(String period) {
            return PeriodCalculator.pl3Period(period, -1);
        }

        @Override
        public String nextPeriod(String period, Integer delta) {
            return PeriodCalculator.pl3AddPeriod(period, delta);
        }

        @Override
        public List<String> lastPeriods(String period, Integer size) {
            return PeriodCalculator.pl3Periods(period, size);
        }

        @Override
        public boolean recommended() {
            return false;
        }

        @Override
        public LotteryEnum parseLottery(String content) {
            return content.contains("排列五") || content.contains("排列5") ? this : null;
        }
    },
    SSQ("ssq", "双色球", (byte) 4, "r25") {

        private final List<Integer> weeks = Lists.newArrayList(2, 4, 7);

        @Override
        public List<Integer> weeks() {
            return weeks;
        }

        @Override
        public boolean freeTime(LocalDateTime time) {
            int week = time.getDayOfWeek().getValue();
            int hour = time.getHour();
            return this.weeks.contains(week) && hour >= 20;
        }

        @Override
        public String lastPeriod(String period) {
            return PeriodCalculator.ssqPeriod(period, 1);
        }

        @Override
        public String lastPeriod(String period, int delta) {
            return PeriodCalculator.ssqPeriod(period, delta);
        }

        @Override
        public String nextPeriod(String period) {
            return PeriodCalculator.ssqAddPeriod(period, 1);
        }

        @Override
        public String nextPeriod(String period, Integer delta) {
            return PeriodCalculator.ssqAddPeriod(period, delta);
        }

        @Override
        public List<String> lastPeriods(String period, Integer size) {
            return PeriodCalculator.ssqPeriods(period, size);
        }

        @Override
        public LotteryEnum parseLottery(String content) {
            return content.contains("双色球") ? this : null;
        }

        @Override
        public int delta(String target, String source) {
            return PeriodCalculator.ssqDelta(target, source);
        }
    },
    DLT("dlt", "大乐透", (byte) 8, "rk3") {

        private final List<Integer> weeks = Lists.newArrayList(1, 3, 6);

        @Override
        public List<Integer> weeks() {
            return weeks;
        }

        @Override
        public boolean freeTime(LocalDateTime time) {
            int week = time.getDayOfWeek().getValue();
            int hour = time.getHour();
            return weeks.contains(week) && hour >= 21;
        }

        @Override
        public String lastPeriod(String period) {
            return PeriodCalculator.dltPeriod(period, 1);
        }

        @Override
        public String lastPeriod(String period, int delta) {
            return PeriodCalculator.dltPeriod(period, delta);
        }

        @Override
        public List<String> lastPeriods(String period, Integer size) {
            return PeriodCalculator.dltPeriods(period, size);
        }

        @Override
        public String nextPeriod(String period) {
            return PeriodCalculator.dltAddPeriod(period, 1);
        }

        @Override
        public String nextPeriod(String period, Integer delta) {
            return PeriodCalculator.dltAddPeriod(period, delta);
        }

        @Override
        public LotteryEnum parseLottery(String content) {
            return content.contains("大乐透") ? this : null;
        }

        @Override
        public int delta(String target, String source) {
            return PeriodCalculator.dltDelta(target, source);
        }
    },
    QLC("qlc", "七乐彩", (byte) 16, "k3") {

        private final List<Integer> weeks = Lists.newArrayList(1, 3, 5);

        @Override
        public List<Integer> weeks() {
            return weeks;
        }

        @Override
        public boolean freeTime(LocalDateTime time) {
            int week = time.getDayOfWeek().getValue();
            int hour = time.getHour();
            return weeks.contains(week) && hour >= 20;
        }

        @Override
        public String lastPeriod(String period) {
            return PeriodCalculator.qlcPeriod(period, 1);
        }

        @Override
        public String lastPeriod(String period, int delta) {
            return PeriodCalculator.qlcPeriod(period, delta);
        }

        @Override
        public String nextPeriod(String period) {
            return PeriodCalculator.qlcAddPeriod(period, 1);
        }

        @Override
        public String nextPeriod(String period, Integer delta) {
            return PeriodCalculator.qlcAddPeriod(period, delta);
        }

        @Override
        public List<String> lastPeriods(String period, Integer size) {
            return PeriodCalculator.qlcPeriods(period, size);
        }

        @Override
        public LotteryEnum parseLottery(String content) {
            return content.contains("七乐彩") || content.contains("7乐彩") ? this : null;
        }

        @Override
        public int delta(String target, String source) {
            return PeriodCalculator.qlcDelta(target, source);
        }
    },
    KL8("kl8", "快乐8", (byte) 32, "d10") {
        @Override
        public boolean freeTime(LocalDateTime time) {
            int hour   = time.getHour();
            int minute = time.getMinute();
            return hour > 20 || (hour == 20 && minute >= 30);
        }

        @Override
        public String lastPeriod(String period) {
            return PeriodCalculator.fc3dPeriod(period, 1);
        }

        @Override
        public String lastPeriod(String period, int delta) {
            return PeriodCalculator.fc3dPeriod(period, delta);
        }

        @Override
        public String nextPeriod(String period) {
            return PeriodCalculator.fc3dPeriod(period, -1);
        }

        @Override
        public String nextPeriod(String period, Integer delta) {
            return PeriodCalculator.fc3dAddPeriod(period, delta);
        }

        @Override
        public List<String> lastPeriods(String period, Integer size) {
            return PeriodCalculator.fc3dPeriods(period, size);
        }

        @Override
        public boolean recommended() {
            return false;
        }

        @Override
        public LotteryEnum parseLottery(String content) {
            return content.contains("快乐8") || content.contains("快乐八") ? this : null;
        }
    };

    private final String type;
    private final String nameZh;
    private final Byte   channel;
    private final String field;

    LotteryEnum(String type, String nameZh, Byte channel, String field) {
        this.type    = type;
        this.nameZh  = nameZh;
        this.channel = channel;
        this.field   = field;
    }

    public String getType() {
        return type;
    }

    public String getNameZh() {
        return nameZh;
    }

    public Byte getChannel() {
        return channel;
    }

    public String getField() {
        return field;
    }

    public abstract boolean freeTime(LocalDateTime time);

    public List<Integer> weeks() {
        return Collections.emptyList();
    }

    public boolean fastTable() {
        return false;
    }

    public abstract String lastPeriod(String period);

    public abstract String lastPeriod(String period, int delta);

    public abstract List<String> lastPeriods(String period, Integer size);

    public abstract String nextPeriod(String period);

    public abstract String nextPeriod(String period, Integer delta);

    public boolean recommended() {
        return true;
    }

    /**
     * 发布时间限制
     *
     * @param time 发布时间
     */
    public boolean issueLimit(LocalDateTime time) {
        int hour   = time.getHour();
        int minute = time.getMinute();
        //如果在开奖日19:30~22:30不允许发布
        boolean limited = ((hour > 19 && hour < 22) || (hour == 19 && minute >= 30) || (hour == 22 && minute <= 30));
        //双色球、大乐透、七乐彩每周开奖
        List<Integer> weeks = weeks();
        if (!CollectionUtils.isEmpty(weeks)) {
            //获取当前日期星期
            int week = time.getDayOfWeek().getValue();
            if (weeks.contains(week)) {
                return limited;
            }
            return false;
        }
        //福彩3D和排列三每天开奖
        return limited;
    }

    @Override
    public String value() {
        return this.type;
    }

    /**
     * 枚举值描述
     */
    @Override
    public String label() {
        return nameZh;
    }

    public static LotteryEnum findOf(String type) {
        return Arrays.stream(values())
                     .filter(v -> v.type.equals(type))
                     .findFirst()
                     .orElseThrow(() -> new RuntimeException("彩票类型未知."));
    }

    public static LotteryEnum findOf(byte channel) {
        return Arrays.stream(values())
                     .filter(v -> v.channel.equals(channel))
                     .findFirst()
                     .orElseThrow(() -> new RuntimeException("彩票频道未知."));
    }

    /**
     * 计算开启彩种集合
     *
     * @param enable 开启数字集合
     */
    public static List<LotteryEnum> enables(int enable) {
        return Arrays.stream(values()).filter(v -> (v.getChannel() & enable) > 0).collect(Collectors.toList());
    }

    public int delta(String target, String source) {
        return 0;
    }

    public abstract LotteryEnum parseLottery(String content);

    public static Optional<LotteryEnum> parseType(String content) {
        return Arrays.stream(values()).filter(e -> e.parseLottery(content) != null).findFirst();
    }

    public static LotteryEnum[] masterValues() {
        return new LotteryEnum[]{FC3D, PL3, DLT, SSQ, QLC};
    }

}
