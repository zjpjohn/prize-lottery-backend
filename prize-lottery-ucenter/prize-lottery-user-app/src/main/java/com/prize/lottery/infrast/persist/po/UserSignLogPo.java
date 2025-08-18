package com.prize.lottery.infrast.persist.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prize.lottery.infrast.persist.enums.SignType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserSignLogPo {

    private Long          id;
    //用户标识
    private Long          userId;
    //签到类型
    private SignType      type;
    //签到奖励
    private Integer       award;
    //签到时间
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
    private LocalDateTime signTime;

}
