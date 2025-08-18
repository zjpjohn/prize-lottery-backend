package com.prize.lottery.adapter.app;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.query.IPackInfoQueryService;
import com.prize.lottery.application.query.vo.PackPrivilegeVo;
import com.prize.lottery.infrast.persist.po.PackInfoPo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@ApiBody
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/pack")
@Permission(domain = LotteryAuth.USER)
public class PackageAppController {

    private final IPackInfoQueryService packInfoQueryService;

    @GetMapping("/list")
    public List<PackInfoPo> packList(@NotNull(message = "用户标识为空") Long userId) {
        return packInfoQueryService.getIssuedPackList(userId);
    }

    @GetMapping("/privilege/list")
    public List<PackPrivilegeVo> privilegeList() {
        return packInfoQueryService.getPrivilegeList();
    }

}
