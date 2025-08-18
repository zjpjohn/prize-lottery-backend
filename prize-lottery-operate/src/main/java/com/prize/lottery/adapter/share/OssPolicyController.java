package com.prize.lottery.adapter.share;

import com.cloud.arch.oss.web.OssPolicy;
import com.cloud.arch.web.annotation.ApiBody;
import com.prize.lottery.application.command.IOssPolicyCommandService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/oss")
@RequiredArgsConstructor
public class OssPolicyController {

    private final IOssPolicyCommandService ossPolicyCommandService;

    @ApiBody
    @GetMapping("/policy")
    public OssPolicy ossPolicy(@NotBlank(message = "文件夹不允许为空") String dir) {
        return ossPolicyCommandService.ossPolicy(dir);
    }

    @PostMapping("/callback")
    public ResponseEntity<Map<String, Object>> ossCallback(HttpServletRequest request,
                                                           @RequestBody(required = false) String body) {
        Map<String, Object> result = ossPolicyCommandService.callbackHandle(request, body);
        return ResponseEntity.ok(result);
    }

}
