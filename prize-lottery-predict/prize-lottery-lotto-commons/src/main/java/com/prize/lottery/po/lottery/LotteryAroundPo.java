package com.prize.lottery.po.lottery;

import com.prize.lottery.enums.AroundType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.AroundResult;
import com.prize.lottery.value.AroundValue;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LotteryAroundPo {

    private Long          id;
    //期号
    private String      period;
    //绕胆图类型
    private AroundType  type;
    //彩种类型
    private LotteryEnum  lotto;
    //绕胆图
    private AroundValue  around;
    //开奖计算结果
    private AroundResult result;
    //开奖日期
    private LocalDate    lottoDate;
    //创建时间
    private LocalDateTime gmtCreate;
    //最新更新时间
    private LocalDateTime gmtModify;

}
