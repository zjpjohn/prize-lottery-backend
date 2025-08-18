package com.prize.lottery.application.service;


import com.prize.lottery.application.cmd.Num3LayerCmd;
import com.prize.lottery.enums.LotteryEnum;

public interface INum3LayerService {

    void createNum3Layer(Num3LayerCmd command);

    void fetchNum3Layer(LotteryEnum type);
    void syncNum3Layer(LotteryEnum type);

    void calcLayerHit(String period, LotteryEnum type);

    void calcLayerHit(Long id);

}
