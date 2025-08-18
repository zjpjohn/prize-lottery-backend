package com.prize.lottery.infrast.spider.open;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;
import com.prize.lottery.delay.AbsDelayTaskExecutor;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.facade.impl.HttpRequestWrapper;
import com.prize.lottery.infrast.props.OpenApiProperties;
import com.prize.lottery.value.ForecastValue;
import com.prize.lottery.value.ICaiForecast;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executor;

@Slf4j
public class OpenForecastFetcher extends AbsDelayTaskExecutor<FetchTask> {

    private final OpenForecastApi    forecastApi;
    private final OpenApiProperties  properties;
    private final HttpRequestWrapper requestWrapper;
    private final FetchEventHandler  fetchEventHandler;

    public OpenForecastFetcher(LotteryEnum lottery,
                               Executor executor,
                               OpenApiProperties properties,
                               FetchEventHandler fetchEventHandler,
                               HttpRequestWrapper requestWrapper) {
        super(executor);
        this.properties        = properties;
        this.forecastApi       = OpenForecastApi.of(lottery);
        this.requestWrapper    = requestWrapper;
        this.fetchEventHandler = fetchEventHandler;
    }

    @Override
    public void executeRequest(FetchTask request) {
        try {
            ICaiForecast forecast = fetchForecast(request.getSourceId(), request.getMasterId(), request.getPeriod());
            if (forecast != null) {
                fetchEventHandler.handle(forecast);
            }
        } catch (Exception e) {
            String lottery = forecastApi.getType().getNameZh();
            log.error("抓取[{}]专家[{}]第[{}]期数据异常:", lottery, request.getMasterId(), request.getPeriod(), e);
        }
    }

    private ICaiForecast fetchForecast(String sourceId, String masterId, String period) {
        Map<String, String> params = Maps.newHashMap();
        params.put("masterId", sourceId);
        params.put("period", period);
        String fetchApi = properties.getBaseUri()
                + forecastApi.fetchApi(properties.getAppKey(), properties.getAppSecret(), params);
        return requestWrapper.getExec(fetchApi, response -> {
            JSONObject root = JSON.parseObject(response);
            if (root.getIntValue("code") != 200) {
                return null;
            }
            JSONObject                 data     = root.getJSONObject("data");
            Map<String, ForecastValue> forecast = forecastApi.parse(data);
            ICaiForecast               result   = new ICaiForecast();
            result.setPeriod(period);
            result.setSourceId(sourceId);
            result.setMasterId(masterId);
            result.setType(forecastApi.getType());
            result.putAll(forecast);
            return result;
        });
    }


    /**
     * 异步增量抓取历史预测数据
     *
     * @param period 待抓取期号
     * @param millis 开始抓取时间
     * @param range  本期抓起时间范围
     */
    public void incrFetchAsync(String period, long millis, int range) {
        List<Pair<String, String>> masters = fetchEventHandler.incrMasters(period);
        if (CollectionUtils.isEmpty(masters)) {
            return;
        }
        Random random = new Random();
        int    bound  = range * 1000;
        for (Pair<String, String> master : masters) {
            long      timestamp = millis + random.nextInt(bound);
            FetchTask fetchTask = new FetchTask(master.getKey(), master.getValue(), period, timestamp);
            this.delayExe(fetchTask);
        }
    }

    /**
     * 异步抓取数据:主要用在抓取历史数据
     *
     * @param period 待抓取期号
     * @param millis 开始抓取时间戳(毫秒)
     * @param range  分散时间范围(秒)
     */
    public void fetchAsync(String period, long millis, int range) {
        List<Pair<String, String>> masters = fetchEventHandler.masters(period);
        int                        bound   = range * 1000;
        if (!CollectionUtils.isEmpty(masters)) {
            Random random = new Random();
            for (Pair<String, String> master : masters) {
                long      timestamp = millis + random.nextInt(bound);
                FetchTask fetchTask = new FetchTask(master.getKey(), master.getValue(), period, timestamp);
                this.delayExe(fetchTask);
            }
        }
    }

    /**
     * 延迟抓取数据：主要用在抓取最新的数据
     *
     * @param period 待抓取期号
     * @param range  延迟范围
     */
    public void fetchDelay(String period, int range) {
        List<Pair<String, String>> masters = fetchEventHandler.masters(period);
        if (CollectionUtils.isEmpty(masters)) {
            return;
        }
        Random random = new Random();
        long   millis = System.currentTimeMillis();
        int    bound  = range * 1000;
        masters.stream().map(v -> {
            long timestamp = millis + random.nextInt(bound);
            return new FetchTask(v.getKey(), v.getValue(), period, timestamp);
        }).forEach(this::delayExe);
    }

}
