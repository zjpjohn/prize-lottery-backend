package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IResourceCommandService;
import com.prize.lottery.application.command.dto.ResourceBatchCmd;
import com.prize.lottery.application.command.dto.ResourceCreateCmd;
import com.prize.lottery.application.command.dto.ResourceEditCmd;
import com.prize.lottery.application.command.dto.ResourceStateCmd;
import com.prize.lottery.application.query.IResourceQueryService;
import com.prize.lottery.application.query.dto.AdmResourceQuery;
import com.prize.lottery.application.vo.ResourceExportVo;
import com.prize.lottery.infrast.persist.po.AppResourcePo;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@ApiBody
@Validated
@RestController
@RequestMapping("/adm/resource")
@Permission(domain = LotteryAuth.MANAGER)
@RequiredArgsConstructor
public class ResourceAdmController {

    private final IResourceCommandService resourceCommandService;
    private final IResourceQueryService   resourceQueryService;

    @PostMapping("/batch")
    public void batchResources(@RequestBody @Validated ResourceBatchCmd command) {
        resourceCommandService.batchAddResources(command);
    }

    @PostMapping("/")
    public void createResource(@RequestBody @Validated ResourceCreateCmd cmd) {
        resourceCommandService.createAppResource(cmd);
    }

    @PutMapping("/")
    public void editResource(@RequestBody @Validated ResourceEditCmd cmd) {
        resourceCommandService.editAppResource(cmd);
    }

    @PutMapping("/switch")
    public void switchResource(@Validated ResourceStateCmd cmd) {
        cmd.getAction().action(resourceCommandService, cmd);
    }

    @PutMapping("/load/{appNo}")
    public void loadResources(@PathVariable String appNo) {
        resourceCommandService.loadResources(appNo);
    }

    @GetMapping("/list")
    public Page<AppResourcePo> resourceList(@Validated AdmResourceQuery query) {
        return resourceQueryService.getAppResourceList(query);
    }

    @GetMapping("/{id}")
    public AppResourcePo resourceDetail(@PathVariable Long id) {
        return resourceQueryService.getAppResourceDetail(id);
    }

    @GetMapping("/export")
    public List<ResourceExportVo> export(@NotBlank(message = "应用标识为空") String appNo) {
        return resourceQueryService.exportAppResource(appNo);
    }

}
