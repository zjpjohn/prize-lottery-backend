package com.prize.lottery.application.vo;

import com.prize.lottery.po.qlc.QlcIcaiPo;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QlcForecastVo extends QlcIcaiPo {

    private StatHitValue red1Hit;
    private StatHitValue red2Hit;
    private StatHitValue red3Hit;
    private StatHitValue red12Hit;
    private StatHitValue red18Hit;
    private StatHitValue red22Hit;
    private StatHitValue kill3Hit;
    private StatHitValue kill6Hit;

}
