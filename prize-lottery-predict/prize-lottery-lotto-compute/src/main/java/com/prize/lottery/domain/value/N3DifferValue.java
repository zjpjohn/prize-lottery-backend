package com.prize.lottery.domain.value;

import com.prize.lottery.value.AnalyzeValue;
import lombok.Data;

import java.util.List;

@Data
public class N3DifferValue {

    //增量计算
    private List<AnalyzeValue> differ76;
    private List<AnalyzeValue> differ75;
    private List<AnalyzeValue> differ73;
    private List<AnalyzeValue> differ65;
    private List<AnalyzeValue> differ63;
    private List<AnalyzeValue> differ62;
    private List<AnalyzeValue> differ32;
    private List<AnalyzeValue> differ31;

    //综合计算
    private List<AnalyzeValue> values;

}
