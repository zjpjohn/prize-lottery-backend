package com.prize.lottery.vo;

import com.prize.lottery.value.MasterValue;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLottoMasterVo {

    private Long          id;
    //用户标识
    private Long          userId;
    //彩票类型
    private String       type;
    //专家信息
    private MasterValue  master;
    //不同指标命中率信息
    private StatHitValue rate1;
    private StatHitValue rate2;
    private StatHitValue  rate3;
    private StatHitValue  rate4;
    private StatHitValue  rate5;
    //时间戳
    private LocalDateTime timestamp;

}
