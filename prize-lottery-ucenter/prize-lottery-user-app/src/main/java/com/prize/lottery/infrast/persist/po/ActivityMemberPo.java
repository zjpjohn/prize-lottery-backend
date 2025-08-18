package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.ActivityState;
import com.prize.lottery.infrast.persist.value.ActivityRemark;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityMemberPo {

    private Long           id;
    private String         name;
    private Integer        duration;
    private Integer        minimum;
    private Integer        throttle;
    private ActivityState  state;
    private ActivityRemark remark;
    private Integer        version;
    private LocalDateTime  gmtCreate;
    private LocalDateTime  gmtModify;

}
