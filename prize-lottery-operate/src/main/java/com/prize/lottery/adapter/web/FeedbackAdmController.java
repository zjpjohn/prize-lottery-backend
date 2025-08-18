package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IFeedTypeCommandService;
import com.prize.lottery.application.command.IFeedbackCommandService;
import com.prize.lottery.application.command.dto.FeedTypeCreateCmd;
import com.prize.lottery.application.command.dto.FeedTypeEditCmd;
import com.prize.lottery.application.command.dto.FeedbackHandleCmd;
import com.prize.lottery.application.query.IFeedTypeQueryService;
import com.prize.lottery.application.query.IFeedbackQueryService;
import com.prize.lottery.application.query.dto.FeedbackListQuery;
import com.prize.lottery.application.query.dto.TypeListQuery;
import com.prize.lottery.application.vo.AppFeedbackVo;
import com.prize.lottery.application.vo.FeedbackTypeVo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@ApiBody
@Validated
@RestController
@RequestMapping("/adm/feedback")
@Permission(domain = LotteryAuth.MANAGER)
@RequiredArgsConstructor
public class FeedbackAdmController {

    private final IFeedbackCommandService commandService;
    private final IFeedbackQueryService   queryService;
    private final IFeedTypeQueryService   typeQueryService;
    private final IFeedTypeCommandService typeCommandService;

    @PutMapping
    public void handleFeedback(@Validated FeedbackHandleCmd handle) {
        commandService.handleFeedback(handle);
    }

    @GetMapping("/{id}")
    public AppFeedbackVo feedback(@PathVariable Long id) {
        return queryService.getAppFeedback(id);
    }

    @GetMapping("/list")
    public Page<AppFeedbackVo> feedbackList(@Validated FeedbackListQuery query) {
        return queryService.getFeedbackList(query);
    }

    @PostMapping("/type")
    public void createFeedType(@Validated FeedTypeCreateCmd command) {
        typeCommandService.createFeedType(command);
    }

    @PutMapping("/type")
    public void editFeedType(@Validated FeedTypeEditCmd command) {
        typeCommandService.editFeedType(command);
    }

    @PutMapping("/type/sort")
    public void sortFeedType(@NotNull(message = "类型标识为空") Long id,
                             @NotNull(message = "排序位置为空") Integer position) {
        typeCommandService.sortFeedType(id, position);
    }

    @DeleteMapping("/type/{id}")
    public void removeType(@NotNull(message = "类型标识为空") @PathVariable Long id) {
        typeCommandService.removeFeedType(id);
    }

    @GetMapping("/type/{id}")
    public FeedbackTypeVo getFeedbackType(@NotNull(message = "类型标识为空") @PathVariable Long id) {
        return typeQueryService.getFeedbackType(id);
    }

    @GetMapping("/type/list")
    public Page<FeedbackTypeVo> feedbackTypes(@Validated TypeListQuery query) {
        return typeQueryService.getFeedbackTypeList(query);
    }

}
