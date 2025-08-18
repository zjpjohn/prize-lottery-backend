package com.prize.lottery.adapter.share;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.web.annotation.ApiBody;
import com.prize.lottery.application.query.service.IAvatarQueryService;
import com.prize.lottery.po.master.AvatarInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
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
@RequestMapping("/share/avatar")
public class AvatarShareController {

    private final IAvatarQueryService avatarQueryService;

    @GetMapping("/avatars/{limit}")
    public List<String> randomAvatars(@Range(min = 1, max = 10, message = "返回数量1~10张") @PathVariable
                                      Integer limit) {
        return avatarQueryService.getRandomAvatars(limit);
    }

    @GetMapping("/avatars")
    public Page<AvatarInfoPo> avatarList(@Validated PageQuery query) {
        return avatarQueryService.getAvatarInfoList(query);
    }
}
