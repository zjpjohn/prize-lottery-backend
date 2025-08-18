package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class AppLauncherLogPo {

    private Long          id;
    private String        deviceId;
    private Long          userId;
    private String        launchIp;
    private LocalDate     launchDate;
    private LocalTime     launchTime;
    private String        launchVersion;
    private LocalDateTime gmtCreate;

}
