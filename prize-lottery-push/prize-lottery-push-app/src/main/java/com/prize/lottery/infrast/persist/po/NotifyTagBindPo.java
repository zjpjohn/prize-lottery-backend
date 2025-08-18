package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotifyTagBindPo {

    private Long          id;
    private Long          appKey;
    private Long          groupId;
    private Long          tagId;
    private String        deviceId;
    private LocalDateTime gmtCreate;

}
