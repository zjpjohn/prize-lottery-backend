package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class AssistantCreateCmd {

    /**
     * 应用标识
     */
    @NotBlank(message = "应用标识为空")
    private String appNo;
    /**
     * 适用应用版本
     */
    private String suitVer;
    /**
     * 助手类型
     */
    @NotBlank(message = "助手类型为空")
    private String type;
    /**
     * 助手标题
     */
    @NotBlank(message = "助手标题为空")
    private String title;
    /**
     * 助手内容
     */
    @NotBlank(message = "助手内容为空")
    private String content;
    /**
     * 备注描述内容
     */
    private String remark;

}
