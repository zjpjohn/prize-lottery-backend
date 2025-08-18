package com.prize.lottery.application.command.vo;

import com.prize.lottery.domain.activity.model.ActivityChance;
import com.prize.lottery.infrast.persist.enums.RaffleState;
import com.prize.lottery.infrast.persist.po.ActivityChancePo;
import com.prize.lottery.infrast.persist.value.RaffleCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActivityChanceVo {

    private Long        id;
    private Integer     type;
    private RaffleCode  code;
    private RaffleState state;

    public ActivityChanceVo(ActivityChancePo chance) {
        this.id    = chance.getId();
        this.type  = chance.getType();
        this.code  = chance.getCode();
        this.state = chance.getState();
    }

    public ActivityChanceVo(ActivityChance chance) {
        this.id    = chance.getId();
        this.type  = chance.getType();
        this.code  = chance.getCode();
        this.state = chance.getState();
    }
}
