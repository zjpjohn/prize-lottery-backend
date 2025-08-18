package com.prize.lottery.vo;

import com.prize.lottery.value.CensusValue;
import lombok.Data;

@Data
public class SyntheticVipCensusVo {

    private String      period;
    private CensusValue level10;
    private CensusValue level20;
    private CensusValue level50;
    private CensusValue level100;
    private CensusValue level150;
}
