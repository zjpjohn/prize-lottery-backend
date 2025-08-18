package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.enums.ConfType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ConfCreateCmd {

    @NotBlank(message = "应用标识为空")
    private String   appNo;
    @NotBlank(message = "配置名称为空")
    private String   confKey;
    @NotBlank(message = "配置值为空")
    private String   confVal;
    @NotNull(message = "配置类型为空")
    private ConfType valType;
    private String   remark;

}
