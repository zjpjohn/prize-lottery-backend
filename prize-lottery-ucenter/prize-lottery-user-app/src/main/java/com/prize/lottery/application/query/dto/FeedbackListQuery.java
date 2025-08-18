package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.FeedbackState;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FeedbackListQuery extends PageQuery {
    private static final long serialVersionUID = -406317904979026288L;

    /**
     * 反馈类型
     */
    private Integer type;
    /**
     * 反馈状态
     */
    @Enumerable(enums = FeedbackState.class, message = "状态参数错误.")
    private Integer state;

}
