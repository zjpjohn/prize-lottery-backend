package com.prize.lottery.domain.activity.model;

import com.cloud.arch.aggregate.Entity;
import com.cloud.arch.utils.IdWorker;
import com.prize.lottery.infrast.persist.enums.RaffleState;
import com.prize.lottery.infrast.persist.value.RaffleCode;
import lombok.Data;

@Data
public class ActivityChance implements Entity<Long> {

    private static final long serialVersionUID = -4109646491727538229L;

    private Long        id;
    private Long       drawId;
    private RaffleCode code;
    private Integer     type;
    private RaffleState state;

    public ActivityChance(Long drawId, Integer type) {
        this.id     = IdWorker.nextId();
        this.drawId = drawId;
        this.type   = type;
        this.code   = RaffleCode.create();
        this.state  = RaffleState.UN_DRAW;
    }

    /**
     * 计算抽奖命中
     *
     * @param lottery 开奖号
     */
    public boolean calcLotteryHit(RaffleCode lottery) {
        boolean result = lottery.isSame(this.code);
        this.state = result ? RaffleState.WIN : RaffleState.LOSS;
        return result;
    }

    /**
     * 系统赠送抽奖机会
     */
    public static ActivityChance system(Long drawId) {
        return new ActivityChance(drawId, 1);
    }

    /**
     * 看激励视频获取抽奖机会
     */
    public static ActivityChance reward(Long drawId) {
        return new ActivityChance(drawId, 2);
    }

}
