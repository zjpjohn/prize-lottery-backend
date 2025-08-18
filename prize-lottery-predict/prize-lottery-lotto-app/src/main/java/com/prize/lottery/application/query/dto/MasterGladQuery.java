package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.enums.LotteryEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MasterGladQuery extends PageQuery {

    private static final long serialVersionUID = -2596471916150041139L;

    private String  period;
    @Enumerable(enums = LotteryEnum.class, message = "彩种类型错误")
    private String  lottery;
    @Enumerable(ranges = {"1", "2"}, message = "喜讯类型错误")
    private Integer type;


}
