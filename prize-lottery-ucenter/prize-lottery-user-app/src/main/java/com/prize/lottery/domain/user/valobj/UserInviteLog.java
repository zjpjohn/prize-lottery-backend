package com.prize.lottery.domain.user.valobj;

import com.prize.lottery.infrast.persist.enums.AgentLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInviteLog {

    private Long       userId;
    private Long       invUid;
    private AgentLevel invAgent;
    private Integer    invReward;

}
