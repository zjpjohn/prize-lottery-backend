package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class CommentListQuery extends PageQuery {

    private static final long serialVersionUID = 4074567143112156614L;

    @NotNull(message = "应用标识为空")
    private String appNo;

}
