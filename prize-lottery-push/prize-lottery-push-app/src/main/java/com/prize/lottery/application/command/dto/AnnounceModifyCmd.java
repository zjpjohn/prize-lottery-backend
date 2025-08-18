package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AnnounceModifyCmd {

    @NotNull(message = "公告标识为空")
    private Long    id;
    private String  title;
    private String  text;
    private String  link;
    @Enumerable(ranges = {"0", "1"}, message = "强制操作标识错误")
    private Integer force;
}
