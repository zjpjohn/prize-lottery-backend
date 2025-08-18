package com.prize.lottery.application.query.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AdminPwdQuery {

    //当前已登录账户标识
    @NotNull(message = "当前账户标识为空")
    private Long managerId;
    //查看密码账户标识
    private Long adminId;

}
