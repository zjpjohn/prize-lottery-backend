package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotifyDevicePo {

    private Long          id;
    private Long          appKey;
    private String        deviceId;
    private String        deviceType;
    private Integer       enable;
    private Long          userId;
    private String        phone;
    private LocalDateTime onlineTime;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
