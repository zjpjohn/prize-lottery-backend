package com.prize.lottery.infrast.external.push.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeviceMetricStat {

    private LocalDateTime time;
    private String        deviceType;
    private Long          count;
    private String        queryType;

    public boolean isNew() {
        return "NEW".equals(this.queryType);
    }
}
