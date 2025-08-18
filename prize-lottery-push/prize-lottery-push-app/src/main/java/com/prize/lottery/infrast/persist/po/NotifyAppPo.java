package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotifyAppPo {

    private Long          id;
    private String        appNo;
    private String        appName;
    private Long          appKey;
    private String        platform;
    private String        remark;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
