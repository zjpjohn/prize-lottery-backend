package com.prize.lottery.vo;

import com.prize.lottery.value.OmitValue;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class LotteryInfoVo {

    private Long            id;
    private String          type;
    private String          period;
    private String          red;
    private String          blue;
    //红球遗漏值
    private List<OmitValue> omitRed;
    //蓝球遗漏值
    private List<OmitValue> omitBlue;
    //额外遗漏参数
    private List<OmitValue> omitExtra;
    //开奖日期
    private LocalDate       lotDate;
}
