package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.Alias;
import com.cloud.arch.page.PageQuery;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MeLoginLogQuery extends PageQuery {

    @Alias("adminId")
    @NotNull(message = "登录用户标识为空")
    private Long managerId;

}
