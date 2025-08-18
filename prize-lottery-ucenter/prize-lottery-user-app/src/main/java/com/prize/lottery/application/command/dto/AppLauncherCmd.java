package com.prize.lottery.application.command.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AppLauncherCmd {

    @NotBlank(message = "设备标识为空")
    private String  deviceId;
    @NotBlank(message = "应用版本为空")
    private String  version;
    private String  brand;
    private String  manufacturer;
    private String  hardware;
    private Integer sdkInt;
    private String  release;
    private String  fromCode;

}
