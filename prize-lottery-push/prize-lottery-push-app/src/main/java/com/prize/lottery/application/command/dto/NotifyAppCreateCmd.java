package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class NotifyAppCreateCmd {

    @NotBlank(message = "应用标识为空")
    private String appNo;
    @NotBlank(message = "应用名称为空")
    private String appName;
    @NotNull(message = "应用key为空")
    private Long   appKey;
    @NotBlank(message = "所属平台为空")
    private String platform;
    private String remark;

}
