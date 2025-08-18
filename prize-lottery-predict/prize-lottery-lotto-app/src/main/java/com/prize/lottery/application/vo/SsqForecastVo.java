package com.prize.lottery.application.vo;

import com.prize.lottery.po.ssq.SsqIcaiPo;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SsqForecastVo extends SsqIcaiPo {

    private StatHitValue red1Hit;
    private StatHitValue red2Hit;
    private StatHitValue red3Hit;
    private StatHitValue red12Hit;
    private StatHitValue red20Hit;
    private StatHitValue red25Hit;
    private StatHitValue rk3Hit;
    private StatHitValue rk6Hit;
    private StatHitValue blue3Hit;
    private StatHitValue blue5Hit;
    private StatHitValue bkHit;

}
