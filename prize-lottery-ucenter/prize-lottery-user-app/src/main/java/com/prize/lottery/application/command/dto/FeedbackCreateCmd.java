package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
public class FeedbackCreateCmd {

    /**
     * 用户标识
     */
    @NotNull(message = "用户标识为空")
    private Long         userId;
    /**
     * 反馈建议类型
     */
    @NotNull(message = "反馈类型为空")
    private Integer      type;
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
