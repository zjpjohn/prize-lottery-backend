package com.prize.lottery.adapter.share;

import com.cloud.arch.web.annotation.ApiBody;
import com.prize.lottery.application.query.IAppVerifyQueryService;
import com.prize.lottery.application.vo.AppVerifyVo;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/uverify")
public class AppVerifyController {

    private final IAppVerifyQueryService appVerifyQueryService;

    @GetMapping("/")
    public AppVerifyVo appVerify(@NotBlank(message = "应用标识为空") String appNo) {
        return appVerifyQueryService.releasedAppVerify(appNo);
    }

}
