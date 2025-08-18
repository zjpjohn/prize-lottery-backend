package com.prize.lottery.application.vo;

import com.prize.lottery.enums.LotteryEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class LotteryPickVo {

    private Long               id;
    private String             period;
    private LotteryEnum        lottery;
    private List<List<String>> reds;
    private List<String>       blues;
    private LocalDateTime      timestamp;

}
