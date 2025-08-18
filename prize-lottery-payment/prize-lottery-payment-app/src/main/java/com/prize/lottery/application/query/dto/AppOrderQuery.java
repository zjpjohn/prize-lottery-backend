package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.prize.lottery.infrast.persist.enums.OrderType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class AppOrderQuery extends PageQuery {

    private static final long serialVersionUID = 8511942128060420752L;

    @NotNull(message = "用户标识为空")
    private Long      userId;
    @NotNull(message = "订单类型为空")
    private OrderType type;

}
