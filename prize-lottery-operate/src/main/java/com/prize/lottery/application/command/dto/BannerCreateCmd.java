package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.valobj.BannerSpecs;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class BannerCreateCmd {

    @NotBlank(message = "应用标识为空")
    private String            appNo;
    @NotBlank(message = "应用页面为空")
    private String            page;
    @Valid
    @NotEmpty(message = "banner集合为空")
    private List<BannerSpecs> items;
    //备注说明
    private String            remark;
}
