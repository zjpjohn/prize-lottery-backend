package com.prize.lottery.value;

import com.google.common.collect.Maps;
import com.prize.lottery.enums.LotteryEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

@Data
public class ICaiForecast implements Serializable {
    private static final long serialVersionUID = 4824102345884038074L;

    private final Map<String, ForecastValue> data = Maps.newHashMap();

    private LotteryEnum type;
    private String      masterId;
    private String      sourceId;
    private String      period;

    public void putAll(Map<String, ForecastValue> value) {
        this.data.putAll(value);
    }

    public void put(String key, String value) {
        this.data.put(key, new ForecastValue(value));
    }

    public ForecastValue get(String key) {
        return this.data.get(key);
    }

    public Map<String, ForecastValue> getData() {
        return Collections.unmodifiableMap(data);
    }

}
