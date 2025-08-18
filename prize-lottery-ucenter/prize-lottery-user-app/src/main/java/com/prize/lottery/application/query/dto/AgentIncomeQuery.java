package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class AgentIncomeQuery extends PageQuery {

    private static final long serialVersionUID = 7353125657248347796L;

    @NotNull(message = "用户标识为空")
    private Long userId;

    @Override
    public PageCondition from() {
        LocalDateTime endTime = LocalDateTime.now();
        //允许查询15天内的数据
        LocalDateTime startTime = endTime.minusDays(15).with(LocalTime.MIN);
        return super.from().setParam("start", startTime).setParam("end", endTime);
    }

}
