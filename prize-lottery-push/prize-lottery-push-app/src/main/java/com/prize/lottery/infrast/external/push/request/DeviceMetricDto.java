package com.prize.lottery.infrast.external.push.request;

import java.time.LocalDateTime;

public record DeviceMetricDto(Long appKey, String queryType, LocalDateTime startTime, LocalDateTime endTime) {

    public static DeviceMetricDto newStat(Long appKey, LocalDateTime startTime, LocalDateTime endTime) {
        return new DeviceMetricDto(appKey, "NEW", startTime, endTime);
    }

    public static DeviceMetricDto totalStat(Long appKey, LocalDateTime startTime, LocalDateTime endTime) {
        return new DeviceMetricDto(appKey, "TOTAL", startTime, endTime);
    }

}
