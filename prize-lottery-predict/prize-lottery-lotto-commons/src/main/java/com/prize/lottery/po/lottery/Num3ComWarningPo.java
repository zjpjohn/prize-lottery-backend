package com.prize.lottery.po.lottery;

import com.prize.lottery.enums.HitType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.WarningComplex;
import com.prize.lottery.value.WarningInt;
import com.prize.lottery.value.WarningText;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Num3ComWarningPo {

    private Long           id;
    private String         period;
    private LotteryEnum    type;
    private WarningComplex dan;
    private WarningComplex twoMa;
    private WarningComplex zu6;
    private WarningComplex zu3;
    private WarningText    kill;
    private WarningInt     kuaList;
    private WarningInt     sumList;
    private HitType        hit;
    private Integer        version;
    private Integer        edits;
    private LocalDateTime  editTime;
    private LocalDateTime  calcTime;
    private LocalDateTime  gmtCreate;
    private LocalDateTime  gmtModify;

}
