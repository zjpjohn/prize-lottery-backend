package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TransferStatisticsPo {

    private Long          id;
    private LocalDate     day;
    private Long          successAmt;
    private Integer       successCnt;
    private Long          failureAmt;
    private Integer       failureCnt;
    private Long          rejectedAmt;
    private Integer       rejectedCnt;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
