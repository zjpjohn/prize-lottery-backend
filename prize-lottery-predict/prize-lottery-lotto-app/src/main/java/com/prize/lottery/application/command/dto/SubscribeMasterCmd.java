package com.prize.lottery.application.command.dto;

import com.prize.lottery.enums.LotteryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeMasterCmd {

    /**
     * 专家标识
     */
    @NotBlank(message = "专家标识为空")
    private String masterId;

    /**
     * 用户标识
     */
    @NotNull(message = "用户标识为空")
    private Long        userId;
    /**
     * 订阅关注渠道
     */
    @NotNull(message = "彩票类型为空")
    private LotteryEnum type;
    /**
     * 订阅时追踪的字段
     */
    private String      trace;

    public SubscribeMasterCmd(String masterId, Long userId, LotteryEnum type) {
        this.masterId = masterId;
        this.userId   = userId;
        this.type     = type;
    }
}
