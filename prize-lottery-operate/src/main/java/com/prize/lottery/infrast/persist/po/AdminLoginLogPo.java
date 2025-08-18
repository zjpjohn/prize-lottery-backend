package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminLoginLogPo {

    private Long          id;
    private Long          adminId;
    private LocalDateTime loginTime;
    private String        loginIp;
    private String        ipRegion;
    private LocalDateTime expireAt;
    private LocalDateTime gmtCreate;

}
