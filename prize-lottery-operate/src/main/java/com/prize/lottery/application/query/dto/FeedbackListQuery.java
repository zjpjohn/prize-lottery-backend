package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.FeedbackState;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FeedbackListQuery extends PageQuery {

    private static final long serialVersionUID = 4081019342950990013L;

    /**
     * 应用编号
     */
    private String  appNo;
    /**
     * 应用版本
     */
    private String  appVersion;
    /**
     * 反馈状态
     */
    @Enumerable(enums = FeedbackState.class, message = "状态码错误")
    private Integer state;

}
