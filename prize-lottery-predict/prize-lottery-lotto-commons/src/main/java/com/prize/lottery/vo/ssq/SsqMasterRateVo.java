package com.prize.lottery.vo.ssq;

import lombok.Data;

@Data
public class SsqMasterRateVo {

    private String period;
    private String masterId;

    private Double red3;
    private Double red3Avg;

    private Double red20;
    private Double red20Avg;

    private Double red25;
    private Double red25Avg;

    private Double rk3;
    private Double rk3Avg;

    private Double blue5;
    private Double blue5Avg;

    private Double bk;
    private Double bkAvg;

}
