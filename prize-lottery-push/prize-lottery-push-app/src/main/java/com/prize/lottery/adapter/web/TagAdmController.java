package com.prize.lottery.adapter.web;


import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.ITagGroupCommandService;
import com.prize.lottery.application.command.dto.TagGroupCreateCmd;
import com.prize.lottery.application.command.dto.TagGroupModifyCmd;
import com.prize.lottery.application.query.INotifyTagQueryService;
import com.prize.lottery.application.query.dto.GroupTagQuery;
import com.prize.lottery.infrast.persist.po.NotifyTagGroupPo;
import com.prize.lottery.infrast.persist.po.NotifyTagPo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@ApiBody
@Validated
@RestController
@RequestMapping("/adm/tag")
@RequiredArgsConstructor
@Permission(domain = LotteryAuth.MANAGER)
public class TagAdmController {

    private final ITagGroupCommandService tagGroupCommandService;
    private final INotifyTagQueryService  notifyTagQueryService;

    @PostMapping("/group")
    public void createTagGroup(@Validated TagGroupCreateCmd command) {
        tagGroupCommandService.createTagGroup(command);
    }

    @PutMapping("/group")
    public void modifyTagGroup(@Validated TagGroupModifyCmd command) {
        tagGroupCommandService.modifyTagGroup(command);
    }

    @GetMapping("/group/{groupId}")
    public NotifyTagGroupPo tagGroup(@PathVariable Long groupId) {
        return notifyTagQueryService.getTagGroup(groupId);
    }

    @GetMapping("/group/list")
    public List<NotifyTagGroupPo> tagGroupList(@NotNull(message = "应用key为空") Long appKey) {
        return notifyTagQueryService.getAppTagGroups(appKey);
    }

    @PostMapping("/group/dilatate")
    public void dilatateGroup(@NotNull(message = "分组标识为空") Long groupId) {
        tagGroupCommandService.dilatateTagGroup(groupId);
    }

    @GetMapping("/list")
    public Page<NotifyTagPo> groupTagList(@Validated GroupTagQuery query) {
        return notifyTagQueryService.getGroupTagList(query);
    }

}
