package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.Ignore;
import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.WithdrawState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdmWithdrawQuery extends PageQuery {

    private static final long serialVersionUID = 3333726482729655923L;

    @Enumerable(enums = WithdrawState.class, message = "提现状态错误.")
    private Integer state;
    @Enumerable(ranges = {"alipay", "wxpay"}, message = "支付渠道错误")
    private String  channel;
    @Ignore
    @Enumerable(ranges = {"1", "7", "14", "30", "60", "90"}, message = "查询天数错误")
    private Integer day;
    //提现单号
    private String  seqNo;

    @Override
    public PageCondition from() {
        PageCondition condition = super.from();
        if (this.day != null) {
            LocalDateTime end   = LocalDateTime.now();
            LocalDateTime start = end.minusDays(this.day).toLocalDate().atTime(LocalTime.MIN);
            condition.setParam("start", start);
            condition.setParam("end", end);
        }
        return condition;
    }
}
