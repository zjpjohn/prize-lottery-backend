package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserBrowseQuery extends PageQuery {

    private static final long serialVersionUID = 2365433606440798489L;

    @NotNull(message = "用户标识为空")
    private Long   userId;
    @NotNull(message = "当前时间为空")
    private Long   current;
    //彩票类型
    @Enumerable(enums = LotteryEnum.class, message = "彩票类型错误")
    private String type;

    @Override
    public PageCondition from() {
        //当前查询时间
        LocalDateTime timestamp = Instant.ofEpochMilli(this.current).atZone(ZoneId.systemDefault()).toLocalDateTime();
        //最多允许查询一个月
        LocalDateTime start = timestamp.minusDays(30).with(LocalTime.MIN);
        return super.from().setParam("timestamp", timestamp).setParam("start", start);
    }

}
