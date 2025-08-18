package com.prize.lottery.vo;

import com.prize.lottery.value.MasterValue;
import lombok.Data;

@Data
public class MasterBattleVo {

    private Long        id;
    //用户标识
    private Long        userId;
    //专家信息
    private MasterValue master;
    //预测期号
    private String      period;
    //预测数据
    private Object      forecast;

}
