package com.prize.lottery.application.query.dto;

import com.cloud.arch.page.PageQuery;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class UserInviteQuery extends PageQuery {

    private static final long serialVersionUID = -5125169021743782890L;

    @NotNull(message = "用户标识为空")
    private Long userId;

}
