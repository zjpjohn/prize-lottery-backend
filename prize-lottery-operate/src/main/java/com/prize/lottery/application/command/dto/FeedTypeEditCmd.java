package com.prize.lottery.application.command.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class FeedTypeEditCmd {

    @NotNull(message = "类型标识为空")
    private Long   id;
    private String type;
    private String suitVer;
    private String remark;

}
