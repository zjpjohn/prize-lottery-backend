package com.prize.lottery.vo.dlt;

import com.prize.lottery.value.CensusValue;
import lombok.Data;

@Data
public class DltChartCensusVo {

    private String      period;
    private CensusValue r1;
    private CensusValue r2;
    private CensusValue r3;
    private CensusValue r10;
    private CensusValue r20;
    private CensusValue rk3;
    private CensusValue rk6;
    private CensusValue b1;
    private CensusValue b2;
    private CensusValue b6;
    private CensusValue bk;

}
