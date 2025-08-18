package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.Ignore;
import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import com.prize.lottery.infrast.persist.enums.VoucherExpire;
import com.prize.lottery.infrast.persist.enums.VoucherLogState;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class AppVoucherLogQuery extends PageQuery {

    private static final long serialVersionUID = 7895266770145684158L;

    @NotNull(message = "用户表示为空")
    private Long    userId;
    @Ignore
    private Integer used;
    @Ignore
    private Integer expired;

    @Override
    public PageCondition from() {
        PageCondition condition = super.from();
        if (used != null) {
            condition.setParam("state", VoucherLogState.ALL_USED.value());
        }
        if (expired != null) {
            condition.setParam("expired", VoucherExpire.EXPIRED.value());
        }
        return condition;
    }
}
