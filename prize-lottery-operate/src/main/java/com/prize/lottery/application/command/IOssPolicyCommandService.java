package com.prize.lottery.application.command;


import com.cloud.arch.oss.web.OssPolicy;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface IOssPolicyCommandService {

    OssPolicy ossPolicy(String directory);

    Map<String, Object> callbackHandle(HttpServletRequest request, String body);

}
