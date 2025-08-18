package com.prize.lottery.vo.ssq;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SsqMasterRankVo {

    private Long        id;
    private MasterValue master;
    private String      period;
    private Integer       vip;
    private Integer       hot;
    /**
     * 浏览次数
     */
    private Integer       browse;
    /**
     * 数据字段类型
     */
    private String        type;
    //本期排名
    private Integer       rank;
    //上一期排名
    private Integer      lastRank;
    //指定类型数据的命中率信息
    private StatHitValue rate;
    //三胆命中率
    private StatHitValue red3;
    //25码命中率
    private StatHitValue  red25;
    //杀三码命中率
    private StatHitValue  rk3;
    //蓝球五码命中率
    private StatHitValue  b5;
    //蓝球杀码命中率
    private StatHitValue  bk;
    private LocalDateTime gmtCreate;
}
