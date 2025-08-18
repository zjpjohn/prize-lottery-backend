package com.prize.lottery.adapter.app;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IPayOrderCommandService;
import com.prize.lottery.application.command.dto.ChargeOrderCreateCmd;
import com.prize.lottery.application.command.dto.MemberOrderCreateCmd;
import com.prize.lottery.application.command.vo.UnionOrderVo;
import com.prize.lottery.application.query.IPayOrderQueryService;
import com.prize.lottery.application.query.dto.AppOrderQuery;
import com.prize.lottery.infrast.persist.enums.OrderType;
import com.prize.lottery.infrast.persist.po.OrderInfoPo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@Permission(domain = LotteryAuth.USER)
public class PayOrderAppController {

    private final IPayOrderQueryService   payOrderQueryService;
    private final IPayOrderCommandService payOrderCommandService;

    @PostMapping("/member")
    public UnionOrderVo prepay(@Validated MemberOrderCreateCmd command) {
        return payOrderCommandService.memberPrepay(command);
    }

    @PostMapping("/charge")
    public UnionOrderVo prepay(@Validated ChargeOrderCreateCmd command) {
        return payOrderCommandService.chargePrepay(command);
    }

    @PostMapping("/repay")
    public UnionOrderVo repayOrder(@NotBlank(message = "订单编号为空") String orderNo) {
        return payOrderCommandService.repayOrder(orderNo);
    }

    @GetMapping("/waiting")
    public String waitPayOrder(@NotNull(message = "订单类型为空")
                               @Enumerable(enums = OrderType.class, message = "订单类型错误") Integer type) {
        return payOrderQueryService.getWaitPayOrder(type);
    }

    @GetMapping("/")
    public OrderInfoPo orderDetail(@NotBlank(message = "订单编号为空") String orderNo,
                                   @NotNull(message = "用户标识为空") Long userId) {
        return payOrderQueryService.getUserOrderInfo(orderNo, userId);
    }

    @GetMapping("/list")
    public Page<OrderInfoPo> userOrderList(@Validated AppOrderQuery query) {
        return payOrderQueryService.getAppOrderList(query);
    }

}
