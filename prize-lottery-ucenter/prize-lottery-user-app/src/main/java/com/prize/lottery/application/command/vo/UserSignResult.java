package com.prize.lottery.application.command.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prize.lottery.infrast.persist.enums.SignType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserSignResult extends UserSignVo {

    //本次签到日志
    private SignLog log;

    @Data
    public static class SignLog {
        private SignType      type;
        private Integer       award;
        @JsonFormat(pattern = "yyyy.MM.dd", timezone = "GMT+8")
        private LocalDateTime signTime;
    }
}
