package com.prize.lottery.domain.activity.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.aggregate.Ignore;
import com.cloud.arch.utils.IdWorker;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.RaffleState;
import com.prize.lottery.infrast.persist.value.RaffleCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Data
@NoArgsConstructor
public class ActivityDrawDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = -9015851183865051659L;

    private Long                 id;
    private Long                 activityId;
    private Long                 userId;
    private LocalDate            day;
    private RaffleCode           code;
    private Integer              times;
    private RaffleState          state;
    private Integer              version;
    @Ignore
    private List<ActivityChance> chances;

    public ActivityDrawDo(Long activityId, Long userId) {
        this.id         = IdWorker.nextId();
        this.activityId = activityId;
        this.userId     = userId;
        this.day        = LocalDate.now();
        this.state      = RaffleState.UN_DRAW;
        this.times      = 1;
        this.version    = 0;
        this.chances    = Lists.newArrayList(ActivityChance.system(this.id));
    }

    /**
     * 是否已抽奖
     */
    public boolean hasDrawn() {
        return this.state != RaffleState.UN_DRAW;
    }

    /**
     * 是否满足抽奖次数限制
     */
    public boolean satisfiedChances(Integer minimum) {
        return this.times >= minimum;
    }

    /**
     * 看激励视频获取抽奖机会
     */
    public void rewardChance() {
        ActivityChance chance = ActivityChance.reward(this.id);
        this.chances.add(chance);
        this.times++;
    }

    /**
     * 发起抽奖动作
     */
    public boolean drawLottery(Integer throttle, Integer duration, ActivityUserDo user) {
        Assert.state(this.state == RaffleState.UN_DRAW, ResponseHandler.DRAW_STATE_ERROR);
        Assert.notNull(user, ResponseHandler.ACTIVITY_USER_NONE);
        this.code = lotteryCode(throttle, user.getLoss());
        boolean raffleResult = false;
        for (ActivityChance chance : this.chances) {
            boolean result = chance.calcLotteryHit(this.code);
            raffleResult = raffleResult || result;
        }
        if (raffleResult) {
            this.state = RaffleState.WIN;
            user.drawSuccess(this.id, LocalDateTime.now(), duration);
        } else {
            this.state = RaffleState.LOSS;
            user.drawLoss(this.id, LocalDateTime.now(), this.times);
        }
        return this.state == RaffleState.WIN;
    }

    /**
     * 生成开奖奖号
     */
    private RaffleCode lotteryCode(Integer throttle, Integer loss) {
        if (loss + times <= throttle + 2) {
            return RaffleCode.create();
        }
        Random         random = new Random(System.nanoTime());
        int            index  = random.nextInt(chances.size());
        ActivityChance chance = this.chances.get(index);
        List<String>   codes  = chance.getCode().shuffle();
        return RaffleCode.create(codes);
    }

    @Override
    public boolean isNew() {
        return this.version == 0;
    }
}
