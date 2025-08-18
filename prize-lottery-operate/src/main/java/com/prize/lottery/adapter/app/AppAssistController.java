package com.prize.lottery.adapter.app;

import com.cloud.arch.web.annotation.ApiBody;
import com.prize.lottery.application.query.IAppAssistQueryService;
import com.prize.lottery.application.query.dto.AppAssistantQuery;
import com.prize.lottery.application.vo.AppAssistantVo;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/assistant")
public class AppAssistController {

    private final IAppAssistQueryService appAssistQueryService;

    @GetMapping("/{id}")
    public AppAssistantVo appAssistant(@PathVariable Long id, @NotBlank(message = "应用标识为空") String appNo) {
        return appAssistQueryService.assistant(id, appNo);
    }

    @GetMapping("/list")
    public List<AppAssistantVo> appAssistants(@Validated AppAssistantQuery query) {
        return appAssistQueryService.appAssistants(query);
    }

}
