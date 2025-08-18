package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class PrivilegeModifyCmd {

    @NotNull(message = "特权标识为空")
    private Long   id;
    @Length(max = 30, message = "特权名称最多输入30个字")
    private String name;
    @Length(max = 200, message = "特权内容最多输入200个字")
    private String content;
    private String icon;

}
