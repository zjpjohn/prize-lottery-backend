package com.prize.lottery.po.lottery;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.LottoIndex;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Num3LottoIndexPo {

    private Long          id;
    private LotteryEnum   type;
    private String        period;
    private LottoIndex    danIndex;
    private LottoIndex    killIndex;
    private LottoIndex    comIndex;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
