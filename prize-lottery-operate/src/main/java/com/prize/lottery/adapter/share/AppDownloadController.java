package com.prize.lottery.adapter.share;

import com.prize.lottery.application.query.IAppInfoQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
@RequestMapping("/app/download")
public class AppDownloadController {

    private final IAppInfoQueryService appInfoQueryService;

    @RequestMapping("/{appNo}")
    public String redirectDownload(@PathVariable String appNo) {
        String downloadUrl = appInfoQueryService.getAppDownloadUrl(appNo);
        return "redirect:" + downloadUrl;
    }

}
