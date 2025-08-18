package com.prize.lottery.application.query.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class AppAssistantQuery {

    @NotBlank(message = "应用表示为空")
    private String  appNo;
    @NotBlank(message = "应用版本为空")
    private String  version;
    private String  type;
    private Boolean detail = false;

}
