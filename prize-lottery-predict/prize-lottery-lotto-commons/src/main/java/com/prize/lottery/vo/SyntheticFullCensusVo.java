package com.prize.lottery.vo;

import com.prize.lottery.value.CensusValue;
import lombok.Data;

@Data
public class SyntheticFullCensusVo {

    private String      period;
    //前100名统计
    private CensusValue level100;
    //前200名统计
    private CensusValue level200;
    //前400名统计
    private CensusValue  level400;
    //前600名统计
    private CensusValue  level600;
    //前800名统计
    private CensusValue  level800;
    //前1000名统计
    private CensusValue  level1000;

}
