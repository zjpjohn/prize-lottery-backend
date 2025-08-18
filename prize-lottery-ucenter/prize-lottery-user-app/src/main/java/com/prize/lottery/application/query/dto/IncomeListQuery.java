package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class IncomeListQuery extends PageQuery {
    private static final long serialVersionUID = 2191128017106189696L;

    /**
     * 用户标识
     */
    @NotNull(message = "用户标识为空")
    private Long   userId;
    /**
     * 彩种类型
     */
    private String type;
    /**
     * 预测期号
     */
    private String period;

}
