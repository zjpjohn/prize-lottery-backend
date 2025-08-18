package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class AppVerifyCreateCmd {

    @NotBlank(message = "应用标识为空")
    private String appNo;
    @NotBlank(message = "应用包名为空")
    private String appPack;
    @NotBlank(message = "应用签名为空")
    private String signature;
    @NotBlank(message = "应用秘钥为空")
    private String authKey;
    @NotBlank(message = "授权成功响应码为空")
    private String success;
    @NotBlank(message = "取消授权响应码为空")
    private String cancel;
    //授权错误降级响应码集合
    private String downgrades;
    //备注说明
    private String remark;

}
