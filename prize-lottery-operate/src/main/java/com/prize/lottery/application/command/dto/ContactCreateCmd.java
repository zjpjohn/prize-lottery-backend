package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ContactCreateCmd {

    @NotBlank(message = "应用标识为空")
    private String appNo;
    @NotBlank(message = "联系人名称为空")
    @Length(max = 20, message = "名称最多20个字")
    private String name;
    @NotBlank(message = "联系人二维码为空")
    private String qrImg;
    @Length(max = 100, message = "说明信息最多100个字")
    private String remark;

}
