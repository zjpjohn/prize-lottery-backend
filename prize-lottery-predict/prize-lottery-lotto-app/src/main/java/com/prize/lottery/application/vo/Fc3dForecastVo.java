package com.prize.lottery.application.vo;

import com.prize.lottery.po.fc3d.Fc3dIcaiPo;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Fc3dForecastVo extends Fc3dIcaiPo {

    private StatHitValue dan1Hit;
    private StatHitValue dan2Hit;
    private StatHitValue dan3Hit;
    private StatHitValue com5Hit;
    private StatHitValue com6Hit;
    private StatHitValue com7Hit;
    private StatHitValue kill1Hit;
    private StatHitValue kill2Hit;
    private StatHitValue comb3Hit;
    private StatHitValue comb4Hit;
    private StatHitValue comb5Hit;

}
