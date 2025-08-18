package com.prize.lottery.value;

import lombok.Data;

@Data
public class StatHitValue {

    //命中项目名称
    private String  term;
    //连续命中次数
    private Integer series;
    //命中率
    private Double  rate;
    //全命中率
    private Double  fullRate;
    //命中率字符串
    private String  count;
}
