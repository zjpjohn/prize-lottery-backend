package com.prize.lottery.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AcctVerifyRepo implements Serializable {

    private static final long serialVersionUID = 694810040994155386L;

    //是否为vip会员
    private boolean member  = false;
    //余额账户是否满足条件
    private boolean balance = false;

}
