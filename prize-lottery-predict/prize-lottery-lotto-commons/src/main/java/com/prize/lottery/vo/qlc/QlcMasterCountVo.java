package com.prize.lottery.vo.qlc;

import com.prize.lottery.value.ForecastValue;
import lombok.Data;

@Data
public class QlcMasterCountVo {

    private String        masterId;
    private String        name;
    private Integer       counts;
    private String        period;
    private ForecastValue data;
    private ForecastValue kill3;
    private ForecastValue kill6;

}
