package com.prize.lottery.adapter.app;

import com.cloud.arch.web.annotation.ApiBody;
import com.prize.lottery.application.query.IResourceQueryService;
import com.prize.lottery.infrast.persist.valobj.AppResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@ApiBody
@Validated
@RestController
@RequestMapping("/app/resource")
@RequiredArgsConstructor
public class ResourceAppController {

    private final IResourceQueryService resourceQueryService;

    @GetMapping("/{appNo}")
    public Map<String, AppResource> resources(@PathVariable String appNo) {
        return resourceQueryService.getAppResources(appNo);
    }

}
