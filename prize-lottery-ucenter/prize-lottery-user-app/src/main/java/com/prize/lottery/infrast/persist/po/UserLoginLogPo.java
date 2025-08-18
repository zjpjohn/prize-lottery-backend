package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLoginLogPo {

    private Long          id;
    private Long          userId;
    private LocalDateTime loginTime;
    private String        loginIp;
    private String        ipRegion;
    private LocalDateTime expireAt;
    private LocalDateTime gmtCreate;

}
