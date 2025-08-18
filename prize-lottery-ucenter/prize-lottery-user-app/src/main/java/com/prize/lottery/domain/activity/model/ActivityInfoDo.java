package com.prize.lottery.domain.activity.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.utils.IdWorker;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.command.dto.ActivityCreateCmd;
import com.prize.lottery.application.command.dto.ActivityEditCmd;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.ActivityState;
import com.prize.lottery.infrast.persist.value.ActivityRemark;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.BiConsumer;

@Slf4j
@Data
@NoArgsConstructor
public class ActivityInfoDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 3489182814784589640L;

    private Long           id;
    //抽奖活动名称
    private String         name;
    //会员有效时间，单位-天
    private Integer        duration;
    //单次抽奖机会最小次数
    private Integer        minimum;
    //单次抽奖达到次数后必中奖,多次抽奖连续未中奖达到次数后必中奖
    private Integer        throttle;
    //活动状态
    private ActivityState  state;
    //活动描述信息
    private ActivityRemark remark;
    //数据版本
    private Integer        version;

    public ActivityInfoDo(ActivityCreateCmd command, BiConsumer<ActivityCreateCmd, ActivityInfoDo> converter) {
        this.id      = IdWorker.nextId();
        this.version = 0;
        converter.accept(command, this);
        this.remark = new ActivityRemark(command.getRemark());
    }

    public void modify(ActivityEditCmd command, BiConsumer<ActivityEditCmd, ActivityInfoDo> converter) {
        Assert.state(this.state != ActivityState.USING, ResponseHandler.DATA_STATE_ILLEGAL);
        converter.accept(command, this);
        List<String> remarkValues = command.getRemark();
        if (CollectionUtils.isNotEmpty(remarkValues)) {
            this.remark = new ActivityRemark(remarkValues);
        }
    }

    public void changeState(ActivityState newState) {
        boolean canTransit = this.state.transitions().contains(newState);
        Assert.state(canTransit, ResponseHandler.DATA_STATE_ILLEGAL);
        this.state = newState;
    }

    @Override
    public boolean isNew() {
        return this.version == 0;
    }

}
