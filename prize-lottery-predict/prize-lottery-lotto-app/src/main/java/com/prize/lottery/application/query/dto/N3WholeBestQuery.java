package com.prize.lottery.application.query.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class N3WholeBestQuery {

    private String  period;
    @NotBlank(message = "字段渠道类型错误")
    @Enumerable(ranges = {"d2", "d3", "c5", "c6", "k1"}, message = "字段渠道错误")
    private String  type;
    @NotNull(message = "查询数量为空")
    @Enumerable(ranges = {"10", "15", "20", "25", "30"}, message = "查询数量为空")
    private Integer limit;

}
