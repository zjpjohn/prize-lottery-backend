package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IAppAssistCommandService;
import com.prize.lottery.application.command.dto.AssistantCreateCmd;
import com.prize.lottery.application.command.dto.AssistantModifyCmd;
import com.prize.lottery.application.query.IAppAssistQueryService;
import com.prize.lottery.application.query.dto.AssistantListQuery;
import com.prize.lottery.infrast.persist.po.AppAssistantPo;
import com.prize.lottery.infrast.persist.valobj.AssistantApp;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@ApiBody
@Validated
@RestController
@RequestMapping("/adm/assistant")
@Permission(domain = LotteryAuth.MANAGER)
@RequiredArgsConstructor
public class AppAssistantController {

    private final IAppAssistQueryService   appAssistQueryService;
    private final IAppAssistCommandService appAssistCommandService;

    @PostMapping("/")
    public void createAssist(@Validated AssistantCreateCmd command) {
        appAssistCommandService.createAssistant(command);
    }

    @PutMapping("/")
    public void modifyAssist(@Validated AssistantModifyCmd command) {
        appAssistCommandService.modifyAssistant(command);
    }

    @PutMapping("/sort")
    public void sortAssistant(@NotNull(message = "助手标识为空") Long id,
                              @NotNull(message = "排序位置为空") Integer position) {
        appAssistCommandService.sortAssistant(id, position);
    }

    @GetMapping("/app/{appNo}")
    public AssistantApp assistantApp(@PathVariable String appNo) {
        return appAssistQueryService.assistantApp(appNo);
    }

    @GetMapping("/list")
    public Page<AppAssistantPo> listQuery(@Validated AssistantListQuery query) {
        return appAssistQueryService.listQuery(query);
    }

    @GetMapping("/{id}")
    public AppAssistantPo assistant(@PathVariable Long id) {
        return appAssistQueryService.assistant(id);
    }

}
