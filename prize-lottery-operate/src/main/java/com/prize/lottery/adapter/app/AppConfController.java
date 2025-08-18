package com.prize.lottery.adapter.app;

import com.cloud.arch.web.annotation.ApiBody;
import com.prize.lottery.application.query.IAppConfQueryService;
import com.prize.lottery.application.vo.AppConfVo;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/app/conf")
public class AppConfController {

    private final IAppConfQueryService appConfQueryService;

    @GetMapping("/{appNo}")
    public List<AppConfVo> appConfList(@PathVariable @NotBlank(message = "应用标识为空") String appNo) {
        return appConfQueryService.getReleasedConfList(appNo);
    }

}
