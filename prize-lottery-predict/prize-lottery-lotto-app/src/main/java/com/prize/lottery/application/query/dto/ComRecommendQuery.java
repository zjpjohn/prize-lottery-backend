package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ComRecommendQuery extends PageQuery {

    private static final long serialVersionUID = 3678971934149737547L;

    @Enumerable(ranges = {"0", "1"}, message = "命中标识错误")
    private Integer hit;

    @Enumerable(ranges = {"1", "2", "3"}, message = "类型标识错误")
    private Integer type;

}

