package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotifyTagPo {

    private Long          id;
    private Long          appKey;
    private Long          groupId;
    private String        tagName;
    private Integer       binds;
    private LocalDateTime gmtCreate;

}
