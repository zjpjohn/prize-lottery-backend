package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.infrast.persist.enums.AdvertChannel;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AdvertiseCreateCmd {

    /**
     * 用户标识
     */
    @NotNull(message = "用户标识为空")
    private Long    userId;
    /**
     * 广告渠道
     */
    @NotNull(message = "广告渠道为空")
    @Enumerable(enums = AdvertChannel.class, message = "广告渠道错误")
    private Integer channel;

}
