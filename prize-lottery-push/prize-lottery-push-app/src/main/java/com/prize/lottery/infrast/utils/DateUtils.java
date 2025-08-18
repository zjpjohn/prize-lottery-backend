package com.prize.lottery.infrast.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

public class DateUtils {

    /**
     * 格式化为 yyyy-MM-ddTHH:mm:ssZ
     *
     * @param time 本地时间
     */
    public static String formatUtc(LocalDateTime time) {
        return time.with(ChronoField.MILLI_OF_SECOND, 0)
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ISO_INSTANT);
    }

    /**
     * 解析utc格式的时间字符串
     *
     * @param timeStr 时间字符串
     */
    public static LocalDateTime parseUtc(String timeStr) {
        return Instant.parse(timeStr).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
