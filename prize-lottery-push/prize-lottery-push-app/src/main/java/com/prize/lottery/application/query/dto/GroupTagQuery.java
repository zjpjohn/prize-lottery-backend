package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class GroupTagQuery extends PageQuery {

    private static final long serialVersionUID = -3511595945467978448L;

    @NotNull(message = "分组标识为空")
    private Long groupId;

}

