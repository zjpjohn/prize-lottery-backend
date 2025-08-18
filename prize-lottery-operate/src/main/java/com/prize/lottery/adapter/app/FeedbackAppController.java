package com.prize.lottery.adapter.app;

import com.cloud.arch.web.annotation.ApiBody;
import com.prize.lottery.application.command.IFeedbackCommandService;
import com.prize.lottery.application.command.dto.FeedbackCreateCmd;
import com.prize.lottery.application.query.IFeedTypeQueryService;
import com.prize.lottery.application.vo.AppFeedTypeVo;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/feedback")
public class FeedbackAppController {

    private final IFeedbackCommandService commandService;
    private final IFeedTypeQueryService   typeQueryService;

    @GetMapping("/type")
    public AppFeedTypeVo feedType(@NotBlank(message = "应用标识为空") String appNo,
                                  @NotBlank(message = "应用版本为空") String version) {
        return typeQueryService.getAppFeedType(appNo, version);
    }

    @PostMapping("/")
    public void createFeedback(@Validated @RequestBody FeedbackCreateCmd command) {
        commandService.createFeedback(command);
    }

}
