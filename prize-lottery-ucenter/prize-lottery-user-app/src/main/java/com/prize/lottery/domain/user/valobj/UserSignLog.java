package com.prize.lottery.domain.user.valobj;

import com.prize.lottery.infrast.persist.enums.SignType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserSignLog {

    private Long     userId;
    private SignType type;
    private Integer  award;
    private LocalDateTime signTime;

}
