package com.prize.lottery.vo;

import com.prize.lottery.value.ForecastValue;
import lombok.Data;

@Data
public class Num3MasterCountVo {

    private String        masterId;
    private String        name;
    private Integer       counts;
    private String        period;
    private ForecastValue data;
    private ForecastValue k1;
    private ForecastValue c7;

}
