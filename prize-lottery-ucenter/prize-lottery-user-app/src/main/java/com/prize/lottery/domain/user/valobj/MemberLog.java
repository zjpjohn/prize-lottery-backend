package com.prize.lottery.domain.user.valobj;

import com.prize.lottery.infrast.persist.enums.TimeUnit;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MemberLog implements Serializable {

    private static final long serialVersionUID = 7683583454735281531L;

    private String        orderNo;
    private String        packNo;
    private String        packName;
    private Integer       type;
    private String        channel;
    private TimeUnit      timeUnit;
    private Long          payed;
    private LocalDateTime expireStart;
    private LocalDateTime expireEnd;

}
