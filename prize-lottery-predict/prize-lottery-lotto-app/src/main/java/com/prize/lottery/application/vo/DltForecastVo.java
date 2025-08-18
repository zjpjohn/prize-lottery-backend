package com.prize.lottery.application.vo;

import com.prize.lottery.po.dlt.DltIcaiPo;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DltForecastVo extends DltIcaiPo {

    private StatHitValue red1Hit;
    private StatHitValue red2Hit;
    private StatHitValue red3Hit;
    private StatHitValue red10Hit;
    private StatHitValue red20Hit;
    private StatHitValue rk3Hit;
    private StatHitValue rk6Hit;
    private StatHitValue blue1Hit;
    private StatHitValue blue2Hit;
    private StatHitValue blue6Hit;
    private StatHitValue bkHit;

}
