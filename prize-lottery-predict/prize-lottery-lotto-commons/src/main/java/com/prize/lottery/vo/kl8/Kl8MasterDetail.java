package com.prize.lottery.vo.kl8;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Kl8MasterDetail {

    private Long          id;
    private String        period;
    private Integer       browse;
    private MasterValue   master;
    private StatHitValue  d6;
    private StatHitValue  d8;
    private StatHitValue  d10;
    private StatHitValue  d15;
    private StatHitValue  k2;
    private StatHitValue  k3;
    //是否有最新预测数据:0-否,1-是
    private Integer       modified   = 0;
    //是否已订阅专家:0-否,1-是
    private Integer       subscribed = 0;
    private LocalDateTime timestamp;
}
