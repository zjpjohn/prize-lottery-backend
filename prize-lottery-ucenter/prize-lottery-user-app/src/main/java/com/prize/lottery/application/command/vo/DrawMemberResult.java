package com.prize.lottery.application.command.vo;

import com.prize.lottery.domain.activity.model.ActivityDrawDo;
import com.prize.lottery.infrast.persist.enums.RaffleState;
import com.prize.lottery.infrast.persist.value.RaffleCode;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class DrawMemberResult {

    private Long                   drawId;
    private Long                   activityId;
    private RaffleCode             lottery;
    private RaffleState            state;
    private List<ActivityChanceVo> chances;

    public DrawMemberResult(ActivityDrawDo draw) {
        this.drawId     = draw.getId();
        this.activityId = draw.getActivityId();
        this.lottery    = draw.getCode();
        this.state      = draw.getState();
        this.chances    = draw.getChances().stream().map(ActivityChanceVo::new).collect(Collectors.toList());
    }
}
