package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.enums.LotteryEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LotterySkillAdmQuery extends PageQuery {

    private static final long serialVersionUID = 5514968131591238184L;

    @Enumerable(enums = LotteryEnum.class, message = "参数错误")
    private String  type;
    private Integer state;

    @Override
    public PageCondition from() {
        return super.from().setParam("sort", 2);
    }
}
