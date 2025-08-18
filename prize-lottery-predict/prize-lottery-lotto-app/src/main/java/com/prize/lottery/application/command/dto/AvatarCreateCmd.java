package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class AvatarCreateCmd {

    @NotBlank(message = "头像标识为空")
    private String key;
    @NotBlank(message = "头像地址为空")
    private String uri;
}
