package com.prize.lottery.application.command.vo;

import com.prize.lottery.domain.activity.model.ActivityDrawDo;
import com.prize.lottery.domain.activity.model.ActivityInfoDo;
import com.prize.lottery.domain.activity.model.ActivityUserDo;
import com.prize.lottery.infrast.persist.enums.RaffleState;
import com.prize.lottery.infrast.persist.value.ActivityRemark;
import com.prize.lottery.infrast.persist.value.RaffleCode;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ActivityJoinResult {

    //本次抽奖标识
    private Long                   drawId;
    //活动标识
    private Long                   activityId;
    //已经抽奖失败机会次数
    private Integer                loss;
    //再抽机会次数必中
    private Integer                againTimes;
    //抽奖日期
    private LocalDate              drawDate;
    //抽奖状态
    private RaffleState            state;
    //开奖码
    private RaffleCode             lottery;
    //本次抽奖机会次数
    private List<ActivityChanceVo> chances;
    //抽奖活动信息
    private ActivityInfo           activity;

    public ActivityJoinResult(ActivityDrawDo draw, ActivityUserDo user, ActivityInfoDo activity) {
        this.drawId     = draw.getId();
        this.activityId = draw.getActivityId();
        this.loss       = user.getLoss();
        this.drawDate   = draw.getDay();
        this.state      = draw.getState();
        this.lottery    = draw.getCode();
        this.activity   = new ActivityInfo(activity);
        this.chances    = draw.getChances().stream().map(ActivityChanceVo::new).collect(Collectors.toList());
        if (this.loss >= activity.getThrottle()) {
            this.againTimes = 5;
        }
    }

    @Data
    public static class ActivityInfo {

        private Long           id;
        private String         name;
        private Integer        duration;
        private Integer        minimum;
        private ActivityRemark remark;

        public ActivityInfo(ActivityInfoDo activity) {
            this.id       = activity.getId();
            this.name     = activity.getName();
            this.duration = activity.getDuration();
            this.minimum  = activity.getMinimum();
            this.remark   = activity.getRemark();
        }
    }

}
