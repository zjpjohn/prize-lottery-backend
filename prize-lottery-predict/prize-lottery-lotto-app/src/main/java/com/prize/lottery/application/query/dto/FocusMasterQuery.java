package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class FocusMasterQuery extends PageQuery {

    private static final long serialVersionUID = -6255034922722222726L;

    @NotNull(message = "用户标识为空")
    private Long userId;

}
