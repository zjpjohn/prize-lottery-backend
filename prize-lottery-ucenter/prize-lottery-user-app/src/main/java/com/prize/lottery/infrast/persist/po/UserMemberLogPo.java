package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.TimeUnit;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserMemberLogPo {

    private Long          id;
    private String        orderNo;
    private Long          userId;
    private String        packNo;
    private String        packName;
    private String        channel;
    private TimeUnit      timeUnit;
    private Integer       type;
    private Long          payed;
    private LocalDateTime expireStart;
    private LocalDateTime expireEnd;
    private LocalDateTime gmtCreate;

}
