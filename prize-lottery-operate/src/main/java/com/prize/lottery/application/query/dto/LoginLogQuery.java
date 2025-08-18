package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.Ignore;
import com.cloud.arch.page.PageQuery;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class LoginLogQuery extends PageQuery {

    private static final long serialVersionUID = 6932678424012963465L;

    @NotNull(message = "账户标识为空")
    private Long adminId;
    @Ignore
    @NotNull(message = "登录账户标识为空")
    private Long managerId;

}
