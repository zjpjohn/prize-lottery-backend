package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityUserPo {

    private Long          id;
    private Long          activityId;
    private Long          userId;
    private Integer       loss;
    private Integer       success;
    private Integer       duration;
    private LocalDateTime lastTime;
    private Long          lastDraw;
    private Integer       version;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;
    
}
