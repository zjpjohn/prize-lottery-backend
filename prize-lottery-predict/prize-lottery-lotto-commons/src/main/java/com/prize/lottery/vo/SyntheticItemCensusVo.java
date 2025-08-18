package com.prize.lottery.vo;

import com.prize.lottery.value.CensusValue;
import lombok.Data;

@Data
public class SyntheticItemCensusVo {

    private String      period;
    //前10名统计
    private CensusValue level10;
    //前20名统计
    private CensusValue level20;
    //前50名统计
    private CensusValue level50;
    //前100名统计
    private CensusValue level100;
    //前150
    private CensusValue level150;
    //前200名统计
    private CensusValue level200;

}
