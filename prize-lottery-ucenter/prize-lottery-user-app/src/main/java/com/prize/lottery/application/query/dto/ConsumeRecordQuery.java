package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import com.prize.lottery.infrast.persist.enums.BalanceAction;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class ConsumeRecordQuery extends PageQuery {

    private static final long serialVersionUID = -1806653038951628879L;

    @NotNull(message = "用户标识为空")
    private Long userId;

    @Override
    public PageCondition from() {
        return super.from().setParam("action", BalanceAction.DEDUCT_BALANCE);
    }
}
