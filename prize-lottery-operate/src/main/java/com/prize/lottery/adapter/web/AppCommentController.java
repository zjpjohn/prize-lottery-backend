package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IAppCommentCommandService;
import com.prize.lottery.application.command.dto.AppCommentBatchCmd;
import com.prize.lottery.application.command.dto.AppCommentCreateCmd;
import com.prize.lottery.application.command.dto.AppCommentEditCmd;
import com.prize.lottery.application.query.IAppCommentQueryService;
import com.prize.lottery.application.query.dto.CommentListQuery;
import com.prize.lottery.infrast.persist.po.AppCommentPo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/adm/comment")
@Permission(domain = LotteryAuth.MANAGER)
public class AppCommentController {

    private final IAppCommentCommandService appCommentCommandService;
    private final IAppCommentQueryService   appCommentQueryService;

    @PostMapping("/batch")
    public void addBatchComments(@Validated @RequestBody AppCommentBatchCmd command) {
        appCommentCommandService.batchComments(command);
    }

    @PostMapping("/")
    public void addComment(@Validated AppCommentCreateCmd command) {
        appCommentCommandService.addAppComment(command);
    }

    @PutMapping("/")
    public void editComment(@Validated AppCommentEditCmd command) {
        appCommentCommandService.editAppComment(command);
    }

    @GetMapping("/")
    public AppCommentPo appComment(@NotNull(message = "评论标识为空") Long id) {
        return appCommentQueryService.appComment(id);
    }

    @GetMapping("/list")
    public Page<AppCommentPo> commentList(@Validated CommentListQuery query) {
        return appCommentQueryService.appCommentList(query);
    }

}
