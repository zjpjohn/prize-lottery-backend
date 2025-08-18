package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnnounceMailboxPo {

    private Long          id;
    private Long          announceId;
    private Long          receiverId;
    private String        channel;
    private LocalDateTime latestRead;
    private LocalDateTime gmtCreate;

}
