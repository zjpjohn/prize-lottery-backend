package com.prize.lottery.infrast.persist.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDrawVo {

    /**
     * 用户标识
     */
    private Long          userId;
    /**
     * 用户名称
     */
    private String        name;
    /**
     * 用户头像
     */
    private String        avatar;
    /**
     * 代金券编号
     */
    private String        bizNo;
    /**
     * 代金券金额
     */
    private Integer       voucher;
    /**
     * 领取时间
     */
    private LocalDateTime timestamp;
}
