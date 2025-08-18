package com.prize.lottery.infrast.external.push.response;

import com.prize.lottery.infrast.persist.enums.PushState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotifyPushRecord {

    private Long          appKey;
    private String        messageId;
    private PushState     state;
    private LocalDateTime pushTime;

}
