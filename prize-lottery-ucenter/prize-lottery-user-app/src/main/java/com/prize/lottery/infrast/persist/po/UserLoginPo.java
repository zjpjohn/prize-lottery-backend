package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLoginPo {

    //用户标识
    private Long          userId;
    //最新一次登录token
    private String        tokenId;
    //token过期截止时间
    private LocalDateTime expireAt;
    //最新一次登录ip地址
    private String        loginIp;
    //最新登录时间
    private LocalDateTime loginTime;
    //当前登录状态
    private Integer       state;
    //上一次登录ip地址
    private String        lastIp;
    //上一次登录时间
    private LocalDateTime lastTime;

}
