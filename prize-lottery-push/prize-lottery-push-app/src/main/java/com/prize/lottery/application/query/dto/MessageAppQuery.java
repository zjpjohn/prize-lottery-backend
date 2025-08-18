package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class MessageAppQuery extends PageQuery {

    private static final long serialVersionUID = 1322814809167918212L;

    @NotNull(message = "用户标识为空")
    private Long   userId;
    @NotBlank(message = "消息渠道为空")
    private String channel;

}
