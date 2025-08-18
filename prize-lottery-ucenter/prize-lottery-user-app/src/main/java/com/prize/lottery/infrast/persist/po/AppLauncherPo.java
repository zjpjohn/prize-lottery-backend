package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AppLauncherPo {

    private Long          id;
    private String        deviceId;
    private LocalDate     launchDate;
    private Integer       launches;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
