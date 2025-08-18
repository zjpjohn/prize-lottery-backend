package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.valobj.ResourceSpecs;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ResourceEditCmd {

    @NotNull(message = "唯一标识为空")
    private Long          id;
    @Valid
    private ResourceSpecs specs;
    private String        name;
    private String        uri;
    private String        defUri;
    private String        remark;

}
