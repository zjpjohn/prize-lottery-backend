package com.prize.lottery.domain.notify.model;

import com.cloud.arch.aggregate.Entity;
import com.prize.lottery.infrast.persist.enums.PushState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotifyTaskDo implements Entity<Long> {

    private static final long serialVersionUID = 7145768274293165215L;

    private Long          id;
    private Long          appKey;
    private Long          groupId;
    private Long          notifyId;
    private String        platform;
    private Integer       tags;
    private String        tagList;
    private PushState     state;
    private LocalDateTime expectTime;
    private LocalDateTime pushTime;
    private String        messageId;

}
