package com.prize.lottery.application.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Num3LotteryVo {

    //上期期号
    private String    lastPeriod;
    //上期开奖号
    private String    last;
    //本期期号
    private String    period;
    //下期期号
    private String    nextPeriod;
    //本期开奖日
    private LocalDate lotDate;
    //当前期开奖号
    private String    current;
    //下期开奖号
    private String    next;
    //下期试机号
    private String    nextShi;
}
