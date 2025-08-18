package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PackStatisticsPo {

    private Long          id;
    private LocalDate     day;
    private String        packNo;
    private Long          successAmt;
    private Integer       successCnt;
    private Long          closedAmt;
    private Integer       closedCnt;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
