package com.prize.lottery.application.vo;

import com.prize.lottery.enums.CodeType;
import com.prize.lottery.enums.LotteryEnum;
import lombok.Data;

import java.util.List;

@Data
public class LotteryCodeVo {

    //开奖类型，仅支持福彩3D和排列三
    private LotteryEnum   lotto;
    //开奖期号
    private String        period;
    //万能码类型
    private CodeType      type;
    //万能码位置
    private List<Integer> positions;
    //开奖号码
    private String        lottery;

}
