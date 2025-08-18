package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.Ignore;
import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.OrderState;
import com.prize.lottery.pay.PayChannel;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdmOrderQuery extends PageQuery {

    private static final long serialVersionUID = -2506036081169495904L;

    @Enumerable(ranges = {"0", "1"}, message = "对账标识错误")
    private Integer settled;
    @Enumerable(enums = PayChannel.class, message = "支付渠道错误")
    private String  channel;
    @Enumerable(enums = OrderState.class, message = "订单状态错误")
    private Integer state;
    @Ignore
    @Pattern(regexp = "^(1([38][0-9]|4[579]|5[0-3,5-9]|66|7[0135678]|9[89])\\d{8})$", message = "手机格式错误")
    private String  phone;
    @Ignore
    @Enumerable(ranges = {"7", "14", "21", "30", "60", "90", "180"}, message = "查询天数错误")
    private Integer day = 7;

    @Override
    public PageCondition from() {
        final PageCondition condition = super.from();
        if (this.day != null) {
            LocalDateTime endDay   = LocalDateTime.now();
            LocalDateTime startDay = endDay.minusDays(this.day - 1).with(LocalTime.MIN);
            condition.setParam("start", startDay);
            condition.setParam("end", endDay);
        }
        return condition;
    }
}
