package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class VerifyPhoneCmd {

    @NotBlank(message = "code参数为空")
    private String code;
    @NotBlank(message = "手机号为空")
    @Pattern(regexp = "^(1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8})$", message = "手机格式错误")
    private String phone;

}
