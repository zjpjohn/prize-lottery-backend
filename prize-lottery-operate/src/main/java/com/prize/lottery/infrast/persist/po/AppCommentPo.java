package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppCommentPo {

    private Long          id;
    private String        appNo;
    private String        name;
    private String        avatar;
    private Integer       rate;
    private String        comment;
    private LocalDateTime cmtTime;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
