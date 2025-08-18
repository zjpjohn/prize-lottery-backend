package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminLoginPo {

    private Long          id;
    private Long          adminId;
    private String        tokenId;
    private LocalDateTime expireAt;
    private String        loginIp;
    private LocalDateTime loginTime;
    private Integer       state;
    private String        lastIp;
    private LocalDateTime lastTime;

}
