package com.prize.lottery.application.query.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CensusLineChartVo {

    private List<String>        xAxis;
    private Map<String, long[]> series;

}
