package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserSignPo {

    //用户标识
    private Long          userId;
    //连续签到次数
    private Integer       series;
    //累计签到总次数
    private Integer       times;
    //累计签到获得总积分
    private Integer       coupon;
    //上一次签到日期
    private LocalDate     lastDate;
    //创建时间
    private LocalDateTime gmtCreate;
    //更新时间
    private LocalDateTime gmtModify;

}
