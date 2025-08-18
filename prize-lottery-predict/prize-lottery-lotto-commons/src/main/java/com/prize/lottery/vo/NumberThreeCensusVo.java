package com.prize.lottery.vo;

import com.prize.lottery.value.CensusValue;
import lombok.Data;

@Data
public class NumberThreeCensusVo {

    private String      name;
    private String      period;
    private CensusValue d1;
    private CensusValue d2;
    private CensusValue d3;
    private CensusValue c5;
    private CensusValue c6;
    private CensusValue c7;
    private CensusValue k1;
    private CensusValue k2;

}
