package com.prize.lottery.application.query.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WithdrawRuleVo {

    private String  scene;
    private Long    throttle;
    private Long    maximum;
    private Integer interval;

}
