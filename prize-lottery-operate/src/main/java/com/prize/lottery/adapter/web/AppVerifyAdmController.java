package com.prize.lottery.adapter.web;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IAppVerifyCommandService;
import com.prize.lottery.application.command.dto.AppVerifyCreateCmd;
import com.prize.lottery.application.command.dto.AppVerifyModifyCmd;
import com.prize.lottery.application.query.IAppVerifyQueryService;
import com.prize.lottery.infrast.persist.po.AppVerifyPo;
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
@RequestMapping("/adm/uverify")
@Permission(domain = LotteryAuth.MANAGER)
public class AppVerifyAdmController {

    private final IAppVerifyQueryService   appVerifyQueryService;
    private final IAppVerifyCommandService appVerifyCommandService;

    @PostMapping("")
    public void createVerify(@Validated AppVerifyCreateCmd command) {
        appVerifyCommandService.createVerify(command);
    }

    @PutMapping("")
    public void modifyVerify(@Validated AppVerifyModifyCmd command) {
        appVerifyCommandService.modifyVerify(command);
    }

    @GetMapping("")
    public AppVerifyPo appVerify(@NotBlank(message = "应用标识为空") String appNo) {
        return appVerifyQueryService.getAppVerifyInfo(appNo);
    }

}
