package com.prize.lottery.value;

import lombok.Data;

@Data
public class BatchItemValue {

    //专家信息
    private MasterValue   master;
    //预测信息
    private ForecastValue forecast;
    //命中率信息
    private StatHitValue  rate;

}
