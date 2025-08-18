package com.prize.lottery.application.command.dto;

import com.cloud.arch.web.validation.annotation.Enumerable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppCommentCreateCmd {

    @NotNull(message = "应用标识为空")
    private String        appNo;
    @NotBlank(message = "评论人名称为空")
    private String        name;
    @NotBlank(message = "评论人头像为空")
    private String        avatar;
    @NotNull(message = "应用评分为空")
    @Enumerable(ranges = {"1", "2", "3", "4", "5"}, message = "评论分数错误")
    private Integer       rate;
    @NotBlank(message = "评论内容为空")
    private String        comment;
    @NotNull(message = "评论时间为空")
    private LocalDateTime cmtTime;

}
