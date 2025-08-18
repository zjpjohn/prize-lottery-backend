package com.prize.lottery.vo.kl8;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Kl8MasterSubscribeVo {

    private Long         id;
    //用户标识
    private Long         userId;
    //专家信息
    private MasterValue  master;
    //选八命中
    private StatHitValue d8;
    //选10命中
    private StatHitValue d10;
    //选14命中
    private StatHitValue d14;
    //杀一命中
    private StatHitValue k1;
    //杀二命中
    private StatHitValue k2;

    private LocalDateTime gmtCreate;

}
