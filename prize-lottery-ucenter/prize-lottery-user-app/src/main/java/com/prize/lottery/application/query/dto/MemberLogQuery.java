package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class MemberLogQuery extends PageQuery {

    private static final long serialVersionUID = 1920197704409773244L;

    @NotNull(message = "用户表示为空")
    private Long userId;

}
