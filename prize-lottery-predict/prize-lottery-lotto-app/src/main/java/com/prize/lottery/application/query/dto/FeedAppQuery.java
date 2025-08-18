package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class FeedAppQuery extends PageQuery {

    private static final long serialVersionUID = 843928544068485283L;

    @NotNull(message = "查询时间为空")
    private LocalDateTime time;

}
