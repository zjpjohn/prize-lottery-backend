package com.prize.lottery.domain.message.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AnnounceMailboxDo implements Serializable {

    private static final long serialVersionUID = 5223292830882162972L;

    private Long          receiverId;
    private String        channel;
    private Long          announceId;
    private LocalDateTime latestRead;

    public AnnounceMailboxDo(Long receiverId, String channel) {
        this.receiverId = receiverId;
        this.channel    = channel;
    }

    public boolean offsetToLatest(Long announceId, LocalDateTime latestRead) {
        if (!isAfter(announceId)) {
            return false;
        }
        this.announceId = announceId;
        this.latestRead = latestRead;
        return true;
    }

    public boolean isEmpty() {
        return this.announceId == null;
    }

    public boolean isAfter(Long announceId) {
        return this.announceId == null || this.announceId < announceId;
    }

}
