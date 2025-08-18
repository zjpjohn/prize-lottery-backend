package com.prize.lottery.application.command.event;

import com.cloud.arch.event.annotations.Publish;
import com.prize.lottery.enums.BrowseType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Publish(name = "cloud-lottery-user-topic", filter = "user-balance-deduct")
public class DeductBalanceEvent implements Serializable {

    private static final long serialVersionUID = 3498931974125532164L;

    //浏览流水号
    private String  seqNo;
    //用户标识
    private Long    userId;
    //彩种类型
    private String  lottery;
    //扣减来源类型:预测、图表、批量对比
    private Integer source;
    //扣减来源说明
    private String  remark;
    //扣减来源类型标识:专家标识
    private String  masterId;
    //数据期号
    private String  period;
    //消费扣减金币
    private Integer expend;

    public DeductBalanceEvent(String seqNo,
                              Long userId,
                              String lottery,
                              BrowseType type,
                              String masterId,
                              String period,
                              Integer expend) {
        this.seqNo    = seqNo;
        this.userId   = userId;
        this.lottery  = lottery;
        this.masterId = masterId;
        this.period   = period;
        this.expend   = expend;
        this.source   = type.getType();
        this.remark   = type.getRemark();
    }

}
