package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AppCommentBatchCmd {

    @NotNull(message = "应用标识为空")
    private String appNo;
    @NotBlank(message = "应用评论JSON数组为空")
    private String comments;


}
