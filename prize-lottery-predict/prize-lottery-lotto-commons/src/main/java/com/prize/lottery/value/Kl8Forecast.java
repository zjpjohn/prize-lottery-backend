package com.prize.lottery.value;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

public class Kl8Forecast {

    private final Map<String, ForecastValue> datas = Maps.newHashMap();

    private String thirdId;
    private String thirdName;
    private String period;

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void put(String channel, ForecastValue value) {
        this.datas.put(channel, value);
    }

    public ForecastValue getValue(String channel) {
        return this.datas.get(channel);
    }

    public Map<String, ForecastValue> getDatas() {
        return Collections.unmodifiableMap(this.datas);
    }

}
