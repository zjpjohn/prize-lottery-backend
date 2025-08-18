package com.prize.lottery.domain.user.model;

import com.prize.lottery.domain.master.model.MasterAccumulate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserFocus {

    private Long             id;
    private Long             userId;
    private String           masterId;
    private MasterAccumulate accumulate;

    public UserFocus(Long userId, String masterId) {
        this.userId   = userId;
        this.masterId = masterId;
    }

    public UserFocus focus() {
        this.accumulate = new MasterAccumulate(masterId).focus();
        return this;
    }

    public UserFocus unFocus() {
        this.accumulate = new MasterAccumulate(masterId).unFocus();
        return this;
    }

}
