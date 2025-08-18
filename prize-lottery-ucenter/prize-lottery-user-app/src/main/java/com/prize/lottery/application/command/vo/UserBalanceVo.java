package com.prize.lottery.application.command.vo;

import com.prize.lottery.infrast.persist.enums.TransferScene;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserBalanceVo {

    //用户标识
    private Long          userId;
    //可提现账户余额
    private Integer       balance;
    //不可提现账户余额
    private Integer       surplus;
    //邀请奖励
    private Integer       invite;
    //积分
    private Integer       coupon;
    //代金券
    private Integer       voucher;
    //提现场景标识
    private TransferScene scene;
    //提现
    private Integer       withdraw;
    //是否可提现(0-否,1-是)
    private Integer       canWithdraw = 0;
    //提现换算成人民币
    private Integer       withRmb;
    //最近提现日期
    private LocalDate     withLatest;

}
