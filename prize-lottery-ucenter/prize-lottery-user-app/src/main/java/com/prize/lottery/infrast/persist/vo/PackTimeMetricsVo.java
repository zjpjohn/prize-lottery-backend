package com.prize.lottery.infrast.persist.vo;

import lombok.Data;

@Data
public class PackTimeMetricsVo {

    private Long    yesterdayAmt;
    private Integer yesterdayCnt;
    private Long    weekAmt;
    private Integer weekCnt;
    private Long    monthAmt;
    private Integer monthCnt;
    private Long    lastMonthAmt;
    private Integer lastMonthCnt;

}
