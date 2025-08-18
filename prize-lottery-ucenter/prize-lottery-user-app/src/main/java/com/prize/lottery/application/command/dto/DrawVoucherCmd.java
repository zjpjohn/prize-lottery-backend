package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class DrawVoucherCmd {

    /**
     * 用户标识
     */
    @NotNull(message = "用户标识为空")
    private Long   userId;
    /**
     * 代金券标识
     */
    @NotBlank(message = "代金券标识为空")
    private String seqNo;

}
