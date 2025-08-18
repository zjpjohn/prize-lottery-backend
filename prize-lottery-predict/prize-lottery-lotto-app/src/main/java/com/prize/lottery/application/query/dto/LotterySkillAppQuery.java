package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LotterySkillAppQuery extends PageQuery {

    private static final long serialVersionUID = 523888494714931239L;

    @Enumerable(ranges = {"fc3d", "pl3", "ssq", "dlt", "qlc", "kl8"}, message = "参数错误")
    private String type;

    @Override
    public PageCondition from() {
        return super.from().setParam("valid", 1).setParam("sort", 2);
    }
}
