package com.prize.lottery.domain.app.model;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.persist.enums.FeedbackState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AppFeedbackDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = -7548552091438838185L;

    private Long          id;
    //应用编号
    private String        appNo;
    //应用版本
    private String        appVersion;
    //设备信息
    private String        device;
    //反馈类型
    private String        type;
    //反馈内容
    private String        content;
    //反馈图片集合
    private List<String>  images;
    //反馈处理状态
    private FeedbackState state;
    //反馈备注
    private String        remark;

    public void handle(FeedbackState state, String remark) {
        Assert.state(this.state == FeedbackState.CREATE, ResponseHandler.FEEDBACK_HAS_HANDLED);
        this.state  = state;
        this.remark = remark;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

}
