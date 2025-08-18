package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.ChannelScope;
import com.prize.lottery.infrast.persist.enums.CommonState;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChannelListQuery extends PageQuery {

    private static final long serialVersionUID = -2449538517002354690L;

    @Enumerable(ranges = {"0", "1"}, message = "渠道类型错误")
    private Integer type;
    @Enumerable(enums = ChannelScope.class, message = "渠道范围错误")
    private Integer scope;
    @Enumerable(ranges = {"0", "1"}, message = "提醒标识错误")
    private Integer remind;
    @Enumerable(enums = CommonState.class, message = "渠道状态错误")
    private Integer state;

}
