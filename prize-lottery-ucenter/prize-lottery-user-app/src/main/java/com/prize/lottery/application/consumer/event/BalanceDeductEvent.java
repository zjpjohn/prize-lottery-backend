package com.prize.lottery.application.consumer.event;

import com.cloud.arch.event.annotations.Subscribe;
import lombok.Data;

import java.io.Serializable;

@Data
@Subscribe(name = "cloud-lottery-user-topic", filter = "user-balance-deduct", key = "seqNo")
public class BalanceDeductEvent implements Serializable {

    private static final long serialVersionUID = -3240349373920609423L;

    //扣减流水号
    private String  seqNo;
    //用户标识
    private Long    userId;
    //彩种类型
    private String  lottery;
    //扣减来源类型:预测、图表、批量对比
    private Integer source;
    //扣减来源说明
    private String  remark;
    //扣减来源类型标识:专家标识等
    private String  masterId;
    //扣减期号
    private String  period;
    //扣减账户奖励金
    private Integer expend;

    public String getEventRemark() {
        return "第" + period + "期" + lottery + remark;
    }

}
