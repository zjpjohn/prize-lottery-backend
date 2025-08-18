package com.prize.lottery.application.vo;

import com.prize.lottery.enums.AroundType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.AroundResult;
import com.prize.lottery.value.AroundValue;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class LotteryAroundVo {

    private String       period;
    private AroundType   type;
    private LotteryEnum  lotto;
    private List<String> balls;
    private AroundValue  around;
    private AroundResult result;
    private LocalDate    date;

}
