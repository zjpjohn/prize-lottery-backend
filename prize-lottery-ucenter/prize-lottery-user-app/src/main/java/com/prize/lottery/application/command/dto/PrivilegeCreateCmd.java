package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class PrivilegeCreateCmd {

    @NotBlank(message = "特权名称为空")
    @Length(max = 30, message = "特权名称最多输入30个字")
    private String name;
    @NotBlank(message = "特权内容为空")
    @Length(max = 200, message = "特权内容最多输入200个字")
    private String content;
    @NotBlank(message = "特权图标为空")
    private String icon;

}
