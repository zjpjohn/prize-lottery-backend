package com.prize.lottery.infrast.persist.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PackDayMetricsVo {

    private LocalDate day;
    private Long      payAmt;
    private Integer   payCnt;

}
