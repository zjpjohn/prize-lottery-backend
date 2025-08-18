package com.prize.lottery.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAccountRepo implements Serializable {

    private static final long serialVersionUID = 5328930518597972430L;

    /**
     * 用户标识
     */
    private Long    userId;
    /**
     * 微信账户openid
     */
    private String  wxId;
    /**
     * 支付宝账户id
     */
    private String  aliId;
    /**
     * 账户状态
     */
    private Integer state;
    /**
     * 账户余额
     */
    private Integer balance;

}
