package com.prize.lottery.vo.dlt;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DltMasterRankVo {

    private Long          id;
    private String        period;
    private Integer       vip;
    private Integer       hot;
    private Integer       rank;
    private Integer       lastRank;
    /**
     * 浏览次数
     */
    private Integer       browse;
    /**
     * 专家信息
     */
    private MasterValue   master;
    //指定类型数据的命中率信息
    private StatHitValue  rate;
    private StatHitValue  red3;
    private StatHitValue  red20;
    private StatHitValue  redKill;
    private StatHitValue  blue;
    private StatHitValue  blueKill;
    private LocalDateTime gmtCreate;

}
