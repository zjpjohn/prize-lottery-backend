package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class DrawBatchVoucherCmd {

    @NotNull(message = "用户标识为空")
    private Long         userId;
    @NotEmpty(message = "代金券列表为空")
    private List<String> seqNos;

}
