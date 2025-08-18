package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.PushState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotifyTaskPo {

    private Long          id;
    private Long          appKey;
    private Long          groupId;
    private Long          notifyId;
    private String        platform;
    private Integer       tags;
    private String        tagList;
    private PushState     state;
    private String        messageId;
    private LocalDateTime expectTime;
    private LocalDateTime pushTime;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
