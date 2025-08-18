package com.prize.lottery.infrast.utils;

import java.time.LocalDateTime;

public class FetchTimeRanges {

    public static final int MAX_LONG = 32000;
    public static final int LONG     = 25000;
    public static final int MIDDLE   = 6600;
    public static final int SHORT    = 1800;

    /**
     * 排列三和福彩3D定时任务抓取时间范围 每天10:20,16:20,18:20更新数据
     *
     * @param time 抓取时间
     */
    public static int n3Range(LocalDateTime time) {
        int hour = time.getHour();
        if (hour == 10) {
            return LONG;
        }
        if (hour == 16) {
            return MIDDLE;
        }
        return SHORT;
    }

    /**
     * 双色球定时抓取预测数据延迟时间范围
     *
     * @param time 开始抓取时间
     */
    public static int ssqRange(LocalDateTime time) {
        int hour = time.getHour();
        int week = time.getDayOfWeek().getValue();
        if (week == 1 || week == 3 || week == 5 || week == 6) {
            //非开奖日每天12,20点抓取
            return hour == 12 ? MAX_LONG : MIDDLE;
        }
        //开奖日每天10,16,18点抓取
        if (hour == 10) {
            return LONG;
        }
        if (hour == 16) {
            return MIDDLE;
        }
        return SHORT;
    }

    /**
     * 大乐透定时抓取预测数据延迟时间范围
     *
     * @param time 开始抓取时间
     */
    public static int dltRange(LocalDateTime time) {
        int hour = time.getHour();
        int week = time.getDayOfWeek().getValue();
        if (week == 2 || week == 4 || week == 5 || week == 7) {
            //非开奖日每天12,20点抓取
            return hour == 12 ? MAX_LONG : MIDDLE;
        }
        //开奖日每天10,16,18点抓取
        if (hour == 10) {
            return LONG;
        }
        if (hour == 16) {
            return MIDDLE;
        }
        return SHORT;
    }

    /**
     * 七乐彩定时抓取预测数据延迟时间范围
     *
     * @param time 开始抓取时间
     */
    public static int qlcRange(LocalDateTime time) {
        int hour = time.getHour();
        int week = time.getDayOfWeek().getValue();

        if (week == 2 || week == 4 || week == 6 || week == 7) {
            //非开奖日每天12,20点抓取
            return hour == 12 ? MAX_LONG : MIDDLE;
        }
        //开奖日每天10,16,18点抓取
        if (hour == 10) {
            return LONG;
        }
        if (hour == 16) {
            return MIDDLE;
        }
        return SHORT;
    }
}
