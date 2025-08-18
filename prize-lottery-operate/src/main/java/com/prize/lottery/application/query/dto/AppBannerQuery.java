package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.prize.lottery.infrast.persist.enums.CommonState;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class AppBannerQuery extends PageQuery {
    private static final long serialVersionUID = -8188032554464136726L;

    @NotNull(message = "应用标识为空")
    private String      appNo;
    private CommonState state;

}
