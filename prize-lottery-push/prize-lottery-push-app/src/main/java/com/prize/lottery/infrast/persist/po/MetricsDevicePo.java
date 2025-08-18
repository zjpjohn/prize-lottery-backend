package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MetricsDevicePo {

    private Long          id;
    private Long          appKey;
    private Long          devices;
    private Long          increases;
    private LocalDate     metricsDate;
    private LocalDateTime latestTime;
    private LocalDateTime gmtCreate;

}
