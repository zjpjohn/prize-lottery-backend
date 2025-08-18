package com.prize.lottery.application.command.dto;

import com.prize.lottery.infrast.persist.enums.AdvertChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AdvertiseFinishCmd {

    /**
     * 用户标识
     */
    @NotNull(message = "用户标识为空")
    private Long   userId;
    /**
     * 广告码
     */
    @NotBlank(message = "广告码为空")
    private String code;

    /**
     * 广告渠道码
     */
    @NotNull(message = "广告渠道为空")
    private AdvertChannel channel;
}
