package com.prize.lottery.vo;

import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

@Data
public class ICaiRankedDataVo {

    private Long          id;
    //专家标识
    private MasterValue   master;
    //预测期号
    private String        period;
    //是否为vip专家
    private Integer       vip;
    //是否为热门专家
    private Integer       hot;
    //双胆标识
    private Integer       mark;
    //单项排名权重
    private Integer       rank;
    //预测数据
    private ForecastValue data;
    //选三杀一码数据
    private ForecastValue kill1;
    //7期命中信息
    private StatHitValue  neRate;
    //15期命中信息
    private StatHitValue  meRate;
    //30期命中信息
    private StatHitValue  hiRate;
}
