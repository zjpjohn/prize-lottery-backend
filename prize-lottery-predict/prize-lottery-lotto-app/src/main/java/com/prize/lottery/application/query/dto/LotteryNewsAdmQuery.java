package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.enums.LotteryEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LotteryNewsAdmQuery extends PageQuery {

    private static final long serialVersionUID = 4036691315990045497L;

    @Enumerable(enums = LotteryEnum.class, message = "参数错误")
    private String  type;
    private Integer state;
    private String  source;

}
