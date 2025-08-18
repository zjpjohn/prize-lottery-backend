package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class MulRankQuery extends PageQuery {

    private static final long serialVersionUID = 3881376594931655373L;

    @NotNull(message = "类型为空")
    @Enumerable(ranges = {"0", "1"}, message = "仅允许0或1")
    private Integer type;
}
