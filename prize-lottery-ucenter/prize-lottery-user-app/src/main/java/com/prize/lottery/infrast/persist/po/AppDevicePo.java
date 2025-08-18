package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppDevicePo {

    private Long          id;
    private String        deviceId;
    private String        brand;
    private String        manufacturer;
    private Integer       sdkInt;
    private String        release;
    private String        hardware;
    private String        fromCode;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
