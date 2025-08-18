package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class FeedbackCreateCmd {
    /**
     * 反馈建议类型
     */
    @NotBlank(message = "反馈类型为空")
    private String       type;
    /**
     * 应用标识
     */
    @NotBlank(message = "应用标识为空")
    private String       appNo;
    /**
     * 应用版本
     */
    @NotBlank(message = "应用版本为空")
    private String       appVersion;
    /**
     * 设备信息
     */
    @NotBlank(message = "设备信息为空")
    private String       device;
    /**
     * 反馈内容
     */
    @NotBlank(message = "反馈内容为空")
    @Length(max = 100, message = "反馈处理备注最多100个字")
    private String       content;
    /**
     * 反馈图片
     */
    @Size(max = 3, message = "反馈图片最多3张")
    private List<String> images;
}
