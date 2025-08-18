package com.prize.lottery.vo.qlc;

import com.prize.lottery.value.CensusValue;
import lombok.Data;

@Data
public class QlcChartCensusVo {

    private String      period;
    private CensusValue r1;
    private CensusValue r2;
    private CensusValue r3;
    private CensusValue r12;
    private CensusValue r18;
    private CensusValue r22;
    private CensusValue k3;
    private CensusValue k6;
}
