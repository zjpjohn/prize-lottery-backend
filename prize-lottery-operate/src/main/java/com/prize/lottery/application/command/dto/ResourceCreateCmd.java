package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.enums.ResourceType;
import com.prize.lottery.infrast.persist.valobj.ResourceSpecs;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ResourceCreateCmd {

    @NotBlank(message = "应用标识为空")
    private String        appNo;
    @NotBlank(message = "资源标识为空")
    private String        feNo;
    @NotBlank(message = "资源名称为空")
    private String        name;
    @NotNull(message = "资源类型为空")
    private ResourceType  type;
    @NotBlank(message = "资源为空")
    private String        uri;
    @NotBlank(message = "默认资源为空")
    private String        defUri;
    @Valid
    @NotNull(message = "资源规格为空")
    private ResourceSpecs specs;
    private String        remark;

}
