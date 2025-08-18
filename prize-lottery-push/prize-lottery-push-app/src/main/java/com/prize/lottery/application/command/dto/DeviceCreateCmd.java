package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class DeviceCreateCmd {

    @NotBlank(message = "应用标识为空")
    private String appNo;
    @NotBlank(message = "设备标识为空")
    private String deviceId;
    @NotBlank(message = "设备类型为空")
    @Enumerable(ranges = {"IOS", "ANDROID"}, message = "设备类型错误")
    private String type;
    @NotBlank(message = "手机号为空")
    @Pattern(regexp = "^(1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8})$", message = "手机格式错误")
    private String phone;
    @NotNull(message = "用户标识为空")
    private Long   userId;

}
