package com.prize.lottery.vo.ssq;

import com.prize.lottery.value.CensusValue;
import lombok.Data;

@Data
public class SsqChartCensusVo {

    private String      period;
    private CensusValue r1;
    private CensusValue r2;
    private CensusValue r3;
    private CensusValue r12;
    private CensusValue r20;
    private CensusValue r25;
    private CensusValue rk3;
    private CensusValue rk6;
    private CensusValue b3;
    private CensusValue b5;
    private CensusValue bk;

}
