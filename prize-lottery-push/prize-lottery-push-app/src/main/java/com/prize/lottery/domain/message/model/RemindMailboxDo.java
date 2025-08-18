package com.prize.lottery.domain.message.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RemindMailboxDo {

    private Long          receiverId;
    private String        channel;
    private Long          remindId;
    private LocalDateTime latestRead;

    public RemindMailboxDo(Long receiverId, String channel) {
        this.receiverId = receiverId;
        this.channel    = channel;
    }


    public boolean offsetToLatest(Long remindId, LocalDateTime latestRead) {
        if (!isAfter(remindId)) {
            return false;
        }
        this.remindId   = remindId;
        this.latestRead = latestRead;
        return true;
    }

    public boolean isEmpty() {
        return this.remindId == null;
    }

    public boolean isAfter(Long remindId) {
        return this.remindId == null || this.remindId < remindId;
    }

}
