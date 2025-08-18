package com.prize.lottery.adapter.web;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.annotation.Permission;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.LotteryAuth;
import com.prize.lottery.application.command.IPackInfoCommandService;
import com.prize.lottery.application.command.dto.PackInfoCreateCmd;
import com.prize.lottery.application.command.dto.PackInfoModifyCmd;
import com.prize.lottery.application.command.dto.PrivilegeCreateCmd;
import com.prize.lottery.application.command.dto.PrivilegeModifyCmd;
import com.prize.lottery.application.query.IPackInfoQueryService;
import com.prize.lottery.application.query.vo.CensusLineChartVo;
import com.prize.lottery.infrast.persist.po.PackInfoPo;
import com.prize.lottery.infrast.persist.po.PackPrivilegePo;
import com.prize.lottery.infrast.persist.vo.PackMetricsVo;
import com.prize.lottery.infrast.persist.vo.PackTimeMetricsVo;
import jakarta.validation.constraints.NotBlank;
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
@RequestMapping("/adm/pack")
@Permission(domain = LotteryAuth.MANAGER)
@RequiredArgsConstructor
public class PackageAdmController {

    private final IPackInfoCommandService packInfoCommandService;
    private final IPackInfoQueryService   packInfoQueryService;

    @PostMapping("/")
    public void createPack(@Validated PackInfoCreateCmd command) {
        packInfoCommandService.createPack(command);
    }

    @PutMapping("/")
    public void editPack(@Validated PackInfoModifyCmd command) {
        packInfoCommandService.modifyPack(command);
    }

    @DeleteMapping("/")
    public void deletePack(@NotBlank(message = "套餐标识为空") String packNo) {
        packInfoCommandService.removePack(packNo);
    }

    @PostMapping("/privilege")
    public void addPrivilege(@Validated PrivilegeCreateCmd command) {
        packInfoCommandService.addPrivilege(command);
    }

    @PutMapping("/privilege")
    public void editPrivilege(@Validated PrivilegeModifyCmd command) {
        packInfoCommandService.editPrivilege(command);
    }

    @DeleteMapping("/privilege")
    public void removePrivilege(@NotNull(message = "特权标识为空") Long pId) {
        packInfoCommandService.removePrivilege(pId);
    }

    @PutMapping("/privilege/sort")
    public void reorderPrivilege(@NotNull(message = "特权标识为空") Long id,
                                 @NotNull(message = "排序标识为空") Integer index) {
        packInfoCommandService.sortPrivilege(id, index);
    }

    @GetMapping("/list")
    public List<PackInfoPo> packList() {
        return packInfoQueryService.getPackList();
    }

    @GetMapping("/using/list")
    public List<PackInfoPo> usingPacks() {
        return packInfoQueryService.getUsingPackList();
    }

    @GetMapping("/")
    public PackInfoPo packInfo(@NotBlank(message = "套餐编号为空") String packNo) {
        return packInfoQueryService.getPackInfo(packNo);
    }

    @GetMapping("/privilege/list")
    public List<PackPrivilegePo> packPrivileges() {
        return packInfoQueryService.getPackPrivileges();
    }

    @GetMapping("/metrics/rank")
    public List<PackMetricsVo> packMetrics() {
        return packInfoQueryService.packMetrics();
    }

    @GetMapping("/metrics/time")
    public PackTimeMetricsVo timeMetrics() {
        return packInfoQueryService.packTimeMetrics();
    }

    @GetMapping("/census/chart")
    public CensusLineChartVo<Long> censusChart(@NotNull(message = "统计天数为空") @Enumerable(
            ranges = {"10", "15", "20", "25", "30"}, message = "查询天数错误") Integer day) {
        return packInfoQueryService.packCensus(day);
    }

}
