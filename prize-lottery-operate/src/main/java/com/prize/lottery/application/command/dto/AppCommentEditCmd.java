package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppCommentEditCmd {

    @NotNull(message = "评论标识为空")
    private Long          id;
    @Enumerable(ranges = {"1", "2", "3", "4", "5"}, message = "评论分数错误")
    private Integer       rate;
    private String        comment;
    private LocalDateTime cmtTime;

}
