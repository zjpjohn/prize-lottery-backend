package com.prize.lottery.vo.ssq;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SsqMasterDetail {

    private Long          id;
    private String        period;
    private MasterValue   master;
    //红球三胆
    private StatHitValue  red3;
    //红球25码
    private StatHitValue  red25;
    //红球杀三码
    private StatHitValue  rk3;
    //蓝球杀码
    private StatHitValue  bk;
    //蓝球五码
    private StatHitValue  b5;
    //是否是vip专家:0-否,1-是
    private Integer       vip        = 0;
    //是否已更新预测数据:0-否,1-是
    private Integer       modified   = 0;
    //追踪专家字段
    private String        trace;
    //追踪专家字段中文名
    private String        traceZh;
    //是否为重点关注:0-否,1-是
    private Integer       special    = 0;
    //用户是否已订阅专家:0-否,1-是
    private Integer       subscribed = 0;
    private LocalDateTime timestamp;
}
