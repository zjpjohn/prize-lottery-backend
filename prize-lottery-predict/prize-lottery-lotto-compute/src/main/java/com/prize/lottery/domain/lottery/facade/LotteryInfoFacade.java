package com.prize.lottery.domain.lottery.facade;


import com.prize.lottery.domain.lottery.model.LotteryInfoDo;
import com.prize.lottery.enums.LotteryEnum;

import java.util.List;

public interface LotteryInfoFacade {

    List<LotteryInfoDo> getLotteryList(LotteryEnum type, Integer size);

    List<LotteryInfoDo> getLotteryList(LotteryEnum type, String start, String end);

    LotteryInfoDo getLatestLotteryInfo(LotteryEnum type);
}
