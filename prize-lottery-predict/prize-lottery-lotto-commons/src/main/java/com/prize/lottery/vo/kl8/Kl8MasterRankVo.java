package com.prize.lottery.vo.kl8;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Kl8MasterRankVo {

    private Long         id;
    //专家信息
    private MasterValue  master;
    //预测期号
    private String       period;
    //本期排名
    private Integer      rank;
    //上一期排名
    private Integer      lastRank;
    //选10命中信息
    private StatHitValue d10;
    //选12命中信息
    private StatHitValue d12;
    //杀1命中
    private StatHitValue k1;
    //杀2命中
    private StatHitValue k2;
    //杀3命中
    private StatHitValue k3;

    private LocalDateTime gmtCreate;

}
