package com.prize.lottery.vo;

import com.prize.lottery.value.BatchItemValue;
import com.prize.lottery.value.Period;
import lombok.Data;

import java.util.List;

@Data
public class BatchCompareVo {

    //数据类型
    private Integer              type;
    //期号信息
    private Period               period;
    //批量数据
    private List<BatchItemValue> items;

}
