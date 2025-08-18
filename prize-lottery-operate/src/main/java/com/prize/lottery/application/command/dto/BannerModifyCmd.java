package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.enums.CommonState;
import com.prize.lottery.infrast.persist.valobj.BannerSpecs;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BannerModifyCmd {

    @NotNull(message = "标识为空")
    private Long              id;
    @Valid
    private List<BannerSpecs> items;
    private String            remark;
    private CommonState       state;
}
