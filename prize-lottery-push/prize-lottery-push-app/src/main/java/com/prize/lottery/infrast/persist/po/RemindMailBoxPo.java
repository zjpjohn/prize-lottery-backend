package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RemindMailBoxPo {

    private Long          id;
    private Long          receiverId;
    private String        channel;
    private Long          remindId;
    private LocalDateTime latestRead;
    private LocalDateTime gmtCreate;

}
