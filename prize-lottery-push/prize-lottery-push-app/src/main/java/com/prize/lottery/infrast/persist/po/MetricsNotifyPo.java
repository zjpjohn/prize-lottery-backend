package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MetricsNotifyPo {

    private Long          id;
    private Long          appKey;
    private Long          sent;
    private Long          accept;
    private Long          receive;
    private Long          opened;
    private Long          deleted;
    private LocalDate     metricsDate;
    private LocalDateTime latestTime;
    private LocalDateTime gmtCreate;

}
