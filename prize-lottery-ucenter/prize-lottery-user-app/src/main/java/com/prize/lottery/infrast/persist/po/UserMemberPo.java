package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.MemberState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserMemberPo {

    private Long          userId;
    private Integer       times;
    private LocalDateTime expireAt;
    private LocalDateTime lastExpire;
    private MemberState   state;
    private LocalDateTime renewTime;
    private Integer       version;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

    public boolean isValid() {
        return this.state == MemberState.NORMAL && this.expireAt.isAfter(LocalDateTime.now());
    }

    public boolean isExpired() {
        return this.expireAt != null && this.expireAt.isAfter(LocalDateTime.now());
    }

}
