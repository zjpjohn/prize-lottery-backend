package com.prize.lottery.vo.dlt;

import lombok.Data;

@Data
public class DltMasterRateVo {

    private String period;
    private String masterId;

    private Double red3;
    private Double red3Avg;

    private Double red10;
    private Double red10Avg;

    private Double red20;
    private Double red20Avg;

    private Double rk3;
    private Double rk3Avg;

    private Double blue6;
    private Double blue6Avg;

    private Double bk;
    private Double bkAvg;

}
