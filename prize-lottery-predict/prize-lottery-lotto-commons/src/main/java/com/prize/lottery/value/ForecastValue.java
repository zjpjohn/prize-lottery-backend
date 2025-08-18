package com.prize.lottery.value;

import com.google.common.base.Splitter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
public class ForecastValue {

    //预测数据
    private String  data;
    //计算后预测命中数据
    private String  hitData;
    //是否命中
    private Integer dataHit;

    public ForecastValue(String data) {
        this(data, null, null);
    }

    public ForecastValue(String hitData, Integer dataHit) {
        this(null, hitData, dataHit);
    }

    public ForecastValue(String data, String hitData, Integer dataHit) {
        this.data    = data;
        this.hitData = hitData;
        this.dataHit = dataHit;
    }

    /**
     * 设置预测命中
     */
    public ForecastValue forecastHit(String hitData, Integer dataHit) {
        return new ForecastValue(this.data, hitData, dataHit);
    }

    /**
     * 分割预测数据
     */
    public List<String> split() {
        return Splitter.on(Pattern.compile("\\s+")).trimResults().omitEmptyStrings().splitToList(data);
    }

}
