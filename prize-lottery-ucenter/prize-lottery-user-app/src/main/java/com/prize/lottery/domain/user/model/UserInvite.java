package com.prize.lottery.domain.user.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.prize.lottery.domain.user.valobj.InviteRewardVal;
import com.prize.lottery.domain.user.valobj.UserInviteLog;
import com.prize.lottery.infrast.persist.enums.AgentLevel;
import com.prize.lottery.infrast.persist.enums.UserState;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInvite implements AggregateRoot<Long> {

    private static final long serialVersionUID = 8590177196046955586L;

    private Long            userId;
    private String          code;
    private String          invUri;
    private Integer         award;
    private Integer    invites;
    private AgentLevel agent;
    private UserState  state;
    private Integer         version;
    private InviteRewardVal rewardVal;
    private UserInviteLog   inviteLog;

    public UserInvite(Long userId, String code, String invUri) {
        this.userId  = userId;
        this.code    = code;
        this.invUri  = invUri;
        this.version = 0;
        this.state   = UserState.NORMAL;
        this.agent   = AgentLevel.NORMAL;
    }

    public void applyAgent(AgentLevel level) {
        this.agent = level;
    }

    public void invite(Long invitedUserId, Integer reward) {
        this.rewardVal = new InviteRewardVal(reward, 1);
        this.inviteLog = new UserInviteLog(invitedUserId, this.userId, this.agent, reward);
    }

    public boolean isNormal() {
        return this.state == UserState.NORMAL;
    }

    public void locked() {
        this.state = UserState.LOCKED;
    }

    @Override
    public void setId(Long id) {
        this.userId = id;
    }

    @Override
    public Long getId() {
        return this.userId;
    }

    @Override
    public boolean isNew() {
        return this.version == 0;
    }

}
