package com.prize.lottery.vo.pl3;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Pl3MasterDetail {

    private Long         id;
    private String       period;
    private MasterValue  master;
    //专家三命中信息
    private StatHitValue dan3;
    //组合六码命中信息
    private StatHitValue com6;
    //组合七码命中信息
    private StatHitValue com7;
    //杀一码命中信息
    private StatHitValue kill1;
    //杀二码命中信息
    private StatHitValue kill2;
    //是否是vip专家:0-否,1-是
    private Integer      vip        = 0;
    //是否有最新预测数据:0-否,1-是
    private Integer      modified   = 0;
    //追踪专家字段
    private String       trace;
    //追踪专家字段中文名
    private String       traceZh;
    //是否为重点关注:0-否,1-是
    private Integer      special    = 0;
    //用户是否已订阅专家：0-否,1-是
    private Integer      subscribed = 0;

    private LocalDateTime timestamp;

}
