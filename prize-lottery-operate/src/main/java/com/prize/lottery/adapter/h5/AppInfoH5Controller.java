package com.prize.lottery.adapter.h5;

import com.cloud.arch.web.annotation.ApiBody;
import com.prize.lottery.application.query.IAppInfoQueryService;
import com.prize.lottery.application.vo.AppHtml5ShareVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ApiBody
@RestController
@RequestMapping("/h5/app")
@RequiredArgsConstructor
public class AppInfoH5Controller {

    private final IAppInfoQueryService appInfoQueryService;

    @GetMapping("/{seqNo}")
    public AppHtml5ShareVo appInfo(@PathVariable String seqNo) {
        return appInfoQueryService.getHtml5AppInfo(seqNo);
    }

}
