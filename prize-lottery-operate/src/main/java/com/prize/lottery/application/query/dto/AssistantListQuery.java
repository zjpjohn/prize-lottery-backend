package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.prize.lottery.infrast.persist.enums.CommonState;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class AssistantListQuery extends PageQuery {

    private static final long serialVersionUID = 3037021906718429118L;

    @NotBlank(message = "应用标识为空")
    private String      appNo;
    private String      type;
    private CommonState state;

}
