package com.prize.lottery.infrast.external.push.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotifyMetricStat {

    private LocalDateTime time;
    private Long          deletedCount;
    private Long          openedCount;
    private Long          receivedCount;
    private Long          sentCount;
    private Long          acceptCount;

}
