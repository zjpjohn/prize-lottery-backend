package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.WithdrawState;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class AppWithdrawQuery extends PageQuery {

    private static final long serialVersionUID = -863358416184990291L;

    @NotNull(message = "用户标识为空")
    private Long    userId;
    @Enumerable(enums = WithdrawState.class, message = "提现状态错误.")
    private Integer state;

}
