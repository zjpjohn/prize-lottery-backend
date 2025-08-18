package com.prize.lottery.domain.user.valobj;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InviteRewardVal {
    /**
     * 邀请奖励金币
     */
    private Integer reward;
    /**
     * 邀请人数
     */
    private Integer invite;
}
