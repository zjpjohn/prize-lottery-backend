package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class AdminPasswordCmd extends ResetPasswordCmd {

    /**
     * 账户标识
     */
    @NotNull(message = "唯一标识为空")
    private Long id;

}
