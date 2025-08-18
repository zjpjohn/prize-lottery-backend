package com.prize.lottery.po.lottery;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.Omit;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LotteryOmitPo {

    public LotteryOmitPo(LotteryEnum type) {
        this.type = type;
    }

    private Long          id;
    //彩票类型
    private LotteryEnum   type;
    //开奖期号
    private String        period;
    //红球遗漏值(包含排三和3D的号码基本遗漏)
    private Omit          red;
    //蓝球遗漏值
    private Omit          blue;
    //百位遗漏
    private Omit          cb1;
    //十位遗漏
    private Omit          cb2;
    //各位遗漏
    private Omit          cb3;
    //排五第四位遗漏
    private Omit          cb4;
    //排五第五位遗漏
    private Omit          cb5;
    //额外遗漏值(主要针对福彩3D和排列三:组三、组六遗漏)
    private Omit          extra;
    //
    private LocalDateTime gmtCreate;

}
