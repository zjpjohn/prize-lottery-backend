package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class FeedTypeCreateCmd {

    @NotBlank(message = "应用标识为空")
    private String appNo;
    @NotBlank(message = "反馈类型为空")
    private String type;
    @NotBlank(message = "适用版本为空")
    private String suitVer;
    private String remark;

}
