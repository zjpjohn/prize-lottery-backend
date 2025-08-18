package com.prize.lottery.domain.master.model;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.Data;

@Data
public class MasterBattle {

    private Long        id;
    private LotteryEnum type;
    private Long        userId;
    private String      masterId;
    private String      period;
    private Integer     sort;
    private Integer     state;

    public MasterBattle(LotteryEnum type, Long userId, String masterId, String period) {
        this.type     = type;
        this.userId   = userId;
        this.masterId = masterId;
        this.period   = period;
        this.sort     = 1;
        this.state    = 1;
    }

    public boolean isRemoved() {
        return this.state == 0;
    }

    public void reAddBattle() {
        this.state = 1;
    }

    public MasterBattle remove() {
        Assert.state(state == 1, ResponseHandler.MASTER_BATTLE_HAS_REMOVED);
        this.state = 0;
        return this;
    }

}
