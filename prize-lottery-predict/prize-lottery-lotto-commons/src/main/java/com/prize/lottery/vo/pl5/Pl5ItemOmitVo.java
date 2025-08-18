package com.prize.lottery.vo.pl5;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.Omit;
import lombok.Data;

@Data
public class Pl5ItemOmitVo {

    private Long        id;
    private String      period;
    private String      balls;
    private LotteryEnum type = LotteryEnum.PL5;
    private Omit        cb;
    private Omit        cbAmp;
    private Omit        cbAod;
    private Omit        cbBos;
    private Omit        cbOe;

}
