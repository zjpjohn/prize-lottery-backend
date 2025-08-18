package com.prize.lottery.infrast.spider.open;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.facade.impl.HttpRequestWrapper;
import com.prize.lottery.infrast.props.OpenApiProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class OpenLayerFetcher {

    private final OpenApiProperties  properties;
    private final HttpRequestWrapper requestWrapper;

    public OpenNum3Layer fetch(String period, LotteryEnum type) {
        String layerApi = OpenForecastApi.of(type).layerApi(properties.getAppKey(), properties.getAppSecret(), period);
        return requestWrapper.getExec(layerApi, response -> {
            JSONObject root = JSON.parseObject(response);
            if (root.getIntValue("code") != 200) {
                return null;
            }
            return root.getObject("data", OpenNum3Layer.class);
        });
    }
}
