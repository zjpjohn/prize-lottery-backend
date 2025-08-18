package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class MasterEnableCmd implements Serializable {

    private static final long serialVersionUID = -7296488642190319726L;

    @NotNull(message = "用户标识为空")
    private Long userId;
    @NotNull(message = "开启频道为空")
    @Enumerable(ranges = {"1", "2", "4", "8", "16", "32"}, message = "彩票频道错误.")
    private Byte enable;

}
