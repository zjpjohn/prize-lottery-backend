package com.prize.lottery.adapter.app;

import com.cloud.arch.page.Page;
import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.dto.UserBrowseQuery;
import com.prize.lottery.application.query.service.IMasterQueryService;
import com.prize.lottery.application.vo.RecentBrowseRecordVo;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.vo.BrowseRecordVo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Validated
@ApiBody
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/app/browse")
@Permission(domain = LotteryAuth.USER)
public class UserBrowseController {

    private final IMasterQueryService masterQueryService;

    @GetMapping("/recent")
    public RecentBrowseRecordVo recentBrowse(@NotNull(message = "用户标识为空") Long userId,
                                             @NotNull(message = "彩票类型为空") LotteryEnum type) {
        return masterQueryService.getRecentBrowseMasters(userId, type);
    }

    @GetMapping("/list")
    public Page<BrowseRecordVo> browseRecords(@Validated UserBrowseQuery query) {
        return masterQueryService.getUserBrowseRecords(query);
    }

}
