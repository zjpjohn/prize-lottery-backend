package com.prize.lottery.infrast.spider.open;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.facade.impl.HttpRequestWrapper;
import com.prize.lottery.infrast.props.OpenApiProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenMasterFetcher {

    private final OpenApiProperties  properties;
    private final HttpRequestWrapper requestWrapper;

    public List<String> loadMasters(LotteryEnum type) {
        String uri = OpenForecastApi.of(type).masterApi(properties.getAppKey(), properties.getAppSecret());
        return requestWrapper.getExec(properties.getBaseUri() + uri, response -> {
            JSONObject root = JSON.parseObject(response);
            if (root.getIntValue("code") != 200) {
                return Collections.emptyList();
            }
            return root.getList("data", String.class);
        });
    }
}
