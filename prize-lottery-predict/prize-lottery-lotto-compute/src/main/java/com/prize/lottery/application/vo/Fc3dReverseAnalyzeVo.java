package com.prize.lottery.application.vo;

import com.prize.lottery.value.AnalyzeValue;
import lombok.Data;

import java.util.List;

@Data
public class Fc3dReverseAnalyzeVo {

    private List<AnalyzeValue> dan3s;
    private List<AnalyzeValue> com5s;
    private List<AnalyzeValue> com6s;
    private List<AnalyzeValue> com7s;
    private List<AnalyzeValue> values;

}
