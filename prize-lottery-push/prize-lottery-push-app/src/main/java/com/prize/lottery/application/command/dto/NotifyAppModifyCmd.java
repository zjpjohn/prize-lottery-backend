package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class NotifyAppModifyCmd {

    @NotNull(message = "唯一标识为空")
    private Long   id;
    private String appName;
    private Long   appKey;
    private String platform;
    private String remark;

}
