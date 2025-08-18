package com.prize.lottery.infrast.persist.vo;

import lombok.Data;

@Data
public class PackMetricsVo {

    private String  packNo;
    private String  packName;
    private Long    successAmt;
    private Integer successCnt;
    private Long    closedAmt;
    private Integer closedCnt;

}
