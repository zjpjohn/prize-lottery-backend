package com.prize.lottery.infrast.persist.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActiveUserVo {

    private Long          userId;
    private String        avatar;
    private String        nickname;
    private String        phone;
    private Integer       launches;
    private String        deviceId;
    private String        appVersion;
    private LocalDateTime launchTime;
    private String        launchIp;

}
