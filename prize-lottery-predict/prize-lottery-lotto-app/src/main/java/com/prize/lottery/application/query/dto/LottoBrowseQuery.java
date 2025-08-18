package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.prize.lottery.enums.BrowseType;
import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class LottoBrowseQuery extends PageQuery {

    private static final long serialVersionUID = 8034800667047344748L;

    @NotBlank(message = "浏览期号为空")
    private String      period;
    @NotNull(message = "浏览类型为空")
    private BrowseType  source;
    @NotNull(message = "开奖类型为空")
    private LotteryEnum type;
    private String      sourceId;

}
