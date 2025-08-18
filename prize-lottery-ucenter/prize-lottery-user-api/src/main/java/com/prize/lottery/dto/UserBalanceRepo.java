package com.prize.lottery.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserBalanceRepo implements Serializable {
    private static final long serialVersionUID = -3462139707193745919L;

    private Long    userId;
    //提现金币账户余额
    private Integer balance;
    //非提现金币账户余额
    private Integer surplus;

}
