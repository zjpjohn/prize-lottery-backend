package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class TagGroupCreateCmd {

    @NotNull(message = "应用key为空")
    private Long    appKey;
    @NotBlank(message = "分组名称为空")
    @Length(max = 30, message = "名称长度不超过30")
    private String  name;
    @NotBlank(message = "标签名称前缀为空")
    @Length(max = 20, message = "前缀长度不超过30")
    private String  tagPrefix;
    @NotNull(message = "上限值为空")
    @Positive(message = "上限值大于0")
    private Integer upperBound;
    @NotBlank(message = "分组描述为空")
    private String  remark;

}
