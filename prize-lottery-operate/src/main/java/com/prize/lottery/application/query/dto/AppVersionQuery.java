package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.VersionState;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class AppVersionQuery extends PageQuery {

    private static final long serialVersionUID = 718929295843512321L;

    /**
     * 用户标识
     */
    @NotNull(message = "应用标识为空")
    private String  appNo;
    /**
     * 应用版本状态
     */
    @Enumerable(enums = VersionState.class, message = "版本状态为空")
    private Integer state;

}
