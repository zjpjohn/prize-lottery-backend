package com.prize.lottery.adapter.share;

import com.cloud.arch.web.annotation.ApiBody;
import com.cloud.arch.web.validation.annotation.Enumerable;
import com.prize.lottery.application.query.service.IMasterQueryService;
import com.prize.lottery.application.vo.MasterInfoDetailVo;
import com.prize.lottery.value.MasterValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@Validated
@ApiBody
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/share/master")
public class MasterShareController {

    private final IMasterQueryService masterQueryService;

    @GetMapping("/{masterId}")
    public MasterInfoDetailVo masterDetail(@PathVariable String masterId,
                                           @NotNull(message = "用户标识为空") Long userId,
                                           @Enumerable(ranges = {"0", "1"}, message = "搜索标识错误")
                                           @NotNull(message = "搜索标识为空") Integer search) {
        return masterQueryService.getMasterInfoDetail(masterId, userId, search);
    }

    @GetMapping("/search")
    public List<MasterValue> matchMaster(@NotBlank(message = "搜索名称为空") String name) {
        return masterQueryService.matchMasterList(name, 16);
    }

    @GetMapping("/hot/list")
    public List<MasterValue> hotMasters() {
        return masterQueryService.getHotMasterList(10);
    }

}
