package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PivotListQuery extends PageQuery {

    private static final long serialVersionUID = 3775201431175999248L;

    @Enumerable(ranges = {"0", "1"}, message = "第一胆码是否命中")
    private Integer best;
    @Enumerable(ranges = {"0", "1"}, message = "第二胆码是否命中")
    private Integer second;

}
