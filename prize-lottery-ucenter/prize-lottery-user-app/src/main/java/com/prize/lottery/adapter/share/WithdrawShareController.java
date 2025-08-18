package com.prize.lottery.adapter.share;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.application.query.IWithdrawQueryService;
import com.prize.lottery.application.query.vo.WithdrawRuleVo;
import com.prize.lottery.infrast.persist.enums.TransferScene;
import com.prize.lottery.infrast.persist.po.WithdrawLevelPo;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/share/withdraw")
public class WithdrawShareController {

    private final IWithdrawQueryService withdrawQueryService;

    @GetMapping("/scenes")
    public List<Map<String, Object>> transferScenes() {
        return withdrawQueryService.transferScenes();
    }

    @GetMapping("/level")
    public WithdrawLevelPo withdrawLevel(@NotBlank(message = "提现场景为空")
                                         @Enumerable(enums = TransferScene.class, message = "提现场景错误")
                                         String scene) {
        return withdrawQueryService.sceneUsingLevel(scene);
    }

    @GetMapping("/rule")
    public WithdrawRuleVo sceneUsingRule(@NotBlank(message = "提现场景为空")
                                         @Enumerable(enums = TransferScene.class, message = "提现场景错误")
                                         String scene) {
        return withdrawQueryService.sceneUsingRule(scene);
    }

}
