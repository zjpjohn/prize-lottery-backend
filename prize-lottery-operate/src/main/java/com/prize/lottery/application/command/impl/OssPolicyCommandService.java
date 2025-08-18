package com.prize.lottery.application.command.impl;

import com.cloud.arch.oss.web.OssPolicy;
import com.cloud.arch.oss.web.OssPolicyGenerator;
import com.cloud.arch.oss.web.UploadCallbackExecutor;
import com.cloud.arch.utils.JsonUtils;
import com.cloud.arch.web.error.ApiBizException;
import com.google.common.collect.Maps;
import com.prize.lottery.application.command.IOssPolicyCommandService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OssPolicyCommandService implements IOssPolicyCommandService {

    private final OssPolicyGenerator     ossPolicyGenerator;
    private final UploadCallbackExecutor uploadCallbackExecutor;

    @Override
    public OssPolicy ossPolicy(String directory) {
        try {
            return ossPolicyGenerator.execute(directory);
        } catch (Exception e) {
            throw new ApiBizException(HttpStatus.INTERNAL_SERVER_ERROR, 500, "生成oss policy错误");
        }
    }

    @Override
    public Map<String, Object> callbackHandle(HttpServletRequest request, String body) {
        Map<String, Object> response = Maps.newHashMap();
        try {
            String result = uploadCallbackExecutor.execute(request, body);
            response.put("status", "ok");
            response.put("response", JsonUtils.toMap(result));
        } catch (Exception e) {
            log.error("oss回调处理异常:", e);
            response.put("status", "failure");
        }
        return response;
    }

}
