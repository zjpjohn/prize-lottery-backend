package com.prize.lottery.application.vo;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.HoneyValue;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class LotteryHoneyVo {

    private String       period;
    private LotteryEnum  type;
    private HoneyValue   honey;
    private List<String> balls;
    private LocalDate    date;

}
