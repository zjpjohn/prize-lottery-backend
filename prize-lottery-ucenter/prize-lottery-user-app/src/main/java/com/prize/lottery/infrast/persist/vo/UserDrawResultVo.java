package com.prize.lottery.infrast.persist.vo;

import com.prize.lottery.application.command.vo.ActivityChanceVo;
import com.prize.lottery.infrast.persist.enums.RaffleState;
import com.prize.lottery.infrast.persist.value.RaffleCode;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDrawResultVo {

    private Long                   drawId;
    private Long                   activityId;
    private String                 name;
    private LocalDate              day;
    private Integer                times;
    private RaffleCode             code;
    private RaffleState            state;
    private LocalDateTime          gmtCreate;
    private List<ActivityChanceVo> chances;

}
