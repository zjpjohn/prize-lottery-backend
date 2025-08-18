package com.prize.lottery.application.vo;

import com.prize.lottery.domain.value.N3DifferValue;
import com.prize.lottery.value.AnalyzeValue;
import lombok.Data;

import java.util.List;

@Data
public class N3DifferAnalyzeVo {

    //前200增量计算
    private N3DifferValue differ200;
    //前500增量计算
    private N3DifferValue differ500;
    //前1000增量计算
    private N3DifferValue      differ1000;
    //整体综合计算
    private List<AnalyzeValue> values;
}
