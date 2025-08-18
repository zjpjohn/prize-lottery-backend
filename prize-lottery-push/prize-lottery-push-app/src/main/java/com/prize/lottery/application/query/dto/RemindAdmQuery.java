package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.Ignore;
import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.event.MessageType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
@EqualsAndHashCode(callSuper = true)
public class RemindAdmQuery extends PageQuery {

    private static final long serialVersionUID = 1374817949922127988L;

    private String  channel;
    @Enumerable(enums = MessageType.class, message = "消息类型错误")
    private Integer type;
    @Ignore
    @Enumerable(ranges = {"7", "14", "30", "60", "90"}, message = "天数集合错误")
    private Integer days;

    @Override
    public PageCondition from() {
        PageCondition condition = super.from();
        if (this.days != null) {
            LocalDateTime endTime   = LocalDateTime.now();
            LocalDateTime startTime = endTime.minusDays(days).with(LocalTime.MIN);
            condition.setParam("start", startTime);
            condition.setParam("end", endTime);
        }
        return condition;
    }
}
