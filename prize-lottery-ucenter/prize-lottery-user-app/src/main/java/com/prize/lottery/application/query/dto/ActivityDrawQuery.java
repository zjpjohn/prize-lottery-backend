package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.prize.lottery.infrast.persist.enums.RaffleState;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityDrawQuery extends PageQuery {

    private static final long serialVersionUID = -4824927464136201398L;

    @NotNull(message = "抽奖活动标识为空")
    private Long        activityId;
    private RaffleState state;

}
