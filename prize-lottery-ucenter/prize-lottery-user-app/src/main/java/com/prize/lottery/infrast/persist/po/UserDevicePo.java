package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDevicePo {

    private Long          id;
    private Long          userId;
    private String        deviceId;
    private LocalDateTime gmtCreate;

}
