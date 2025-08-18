package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserBalancePo {

    private Long          userId;
    private Integer       invite;
    private Integer       balance;
    private Integer       surplus;
    private Integer       coupon;
    private Integer       voucher;
    private Integer       withdraw;
    private Integer       withRmb;
    private LocalDate     withLatest;
    private Integer       canWith;
    private Integer       canProfit;
    private Integer       version;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
