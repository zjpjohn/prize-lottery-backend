package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class NotifyDeviceQuery extends PageQuery {

    private static final long serialVersionUID = 803568660541596107L;

    @NotNull(message = "应用key为空")
    private Long    appKey;
    @Enumerable(ranges = {"IOS", "ANDROID"}, message = "设备类型错误")
    private String  type;
    @Enumerable(ranges = {"0", "1"}, message = "参数错误")
    private Integer enable;
    @Positive(message = "天数必须正数")
    private Integer days;

    @Override
    public PageCondition from() {
        PageCondition condition = super.from();
        if (days != null) {
            LocalDateTime end   = LocalDateTime.now();
            LocalDateTime start = end.minusDays(days).withHour(0).withMinute(0).withSecond(0);
            condition.setParam("start", start).setParam("end", end);
        }
        return condition;
    }
}
