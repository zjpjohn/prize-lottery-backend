package com.prize.lottery.domain.activity.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.utils.IdWorker;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ActivityUserDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 9102302454093447556L;

    private Long          id;
    private Long          activityId;
    private Long          userId;
    private Integer       loss;
    private Integer       success;
    private Integer       duration;
    private LocalDateTime lastTime;
    private Long          lastDraw;
    private Integer       version;

    public ActivityUserDo(Long activityId, Long userId) {
        this.id         = IdWorker.nextId();
        this.activityId = activityId;
        this.userId     = userId;
        this.loss       = 0;
        this.success    = 0;
        this.duration   = 0;
        this.version    = 0;
    }

    /**
     * 判断用户是否允许对抽奖活动进行抽奖
     */
    public boolean drawPreCheck() {
        //可重复抽奖判断抽奖时间间隔
        return lastTime == null || LocalDate.now().isAfter(lastTime.toLocalDate());
    }

    /**
     * 本次抽奖成功
     *
     * @param drawId   抽奖标识
     * @param drawTime 抽奖时间
     * @param duration 抽中会员有效期
     */
    public void drawSuccess(Long drawId, LocalDateTime drawTime, Integer duration) {
        this.loss = 0;
        this.success++;
        this.duration = this.duration + duration;
        this.lastDraw = drawId;
        this.lastTime = drawTime;
    }

    /**
     * 本次抽奖失败
     *
     * @param drawId   抽奖标识
     * @param drawTime 抽奖时间
     * @param chances  本次抽奖所用机会次数
     */
    public void drawLoss(Long drawId, LocalDateTime drawTime, Integer chances) {
        this.loss     = this.loss + chances;
        this.lastDraw = drawId;
        this.lastTime = drawTime;
    }

    @Override
    public boolean isNew() {
        return this.version == 0;
    }
}
