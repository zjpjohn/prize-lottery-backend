package com.prize.lottery.adapter.web;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IAppInfoCommandService;
import com.prize.lottery.application.command.dto.ContactCreateCmd;
import com.prize.lottery.application.command.dto.ContactEditCmd;
import com.prize.lottery.application.query.IAppInfoQueryService;
import com.prize.lottery.application.query.dto.ContactListQuery;
import com.prize.lottery.infrast.persist.po.AppContactPo;
import jakarta.validation.constraints.NotBlank;
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
@RequestMapping("/adm/contact")
@Permission(domain = LotteryAuth.MANAGER)
public class AppContactController {

    private final IAppInfoQueryService   appQueryService;
    private final IAppInfoCommandService appCommandService;

    @PostMapping("/")
    public void createContact(@Validated ContactCreateCmd command) {
        appCommandService.createContact(command);
    }

    @PutMapping("/")
    public void editContact(@Validated ContactEditCmd command) {
        appCommandService.editContact(command);
    }

    @DeleteMapping("/")
    public void clearInvalid(@NotBlank(message = "应用标识为空") String appNo) {
        appCommandService.removeContacts(appNo);
    }

    @GetMapping("/")
    public AppContactPo contact(@NotNull(message = "联系人标识为空") Long id) {
        return appQueryService.appContact(id);
    }

    @GetMapping("/list")
    public Page<AppContactPo> contactList(@Validated ContactListQuery query) {
        return appQueryService.appContactList(query);
    }

}
