package com.prize.lottery.application.vo;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CensusLineChartVo<T> {

    private static final String DEFAULT = "default";

    private List<String>     xAxis;
    private Map<String, T[]> series;

    public CensusLineChartVo(List<String> xAxis, T[] data) {
        this.xAxis  = xAxis;
        this.series = Maps.newHashMap();
        this.series.put(DEFAULT, data);
    }

}
