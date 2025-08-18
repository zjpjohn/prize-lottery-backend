package com.prize.lottery.application.service;


import com.prize.lottery.application.cmd.Num3ComWarnCmd;
import com.prize.lottery.enums.LotteryEnum;

public interface INum3ComWarnService {

    void createComWarn(Num3ComWarnCmd cmd);

    void calcComWarnHit(LotteryEnum type, String period);

    void calcWarnHit(Long id);

}
