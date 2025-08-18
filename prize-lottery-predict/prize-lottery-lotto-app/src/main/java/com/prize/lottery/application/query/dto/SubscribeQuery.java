package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class SubscribeQuery extends PageQuery {
    private static final long serialVersionUID = -2918620202645484292L;

    @NotNull(message = "用户标识为空")
    private Long userId;

    public PageCondition from(String period) {
        return this.from().setParam("period", period);
    }
}
