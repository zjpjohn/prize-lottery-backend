package com.prize.lottery.domain.lottery.facade;


import com.prize.lottery.domain.lottery.model.LotteryTrialDo;
import com.prize.lottery.enums.LotteryEnum;

import java.util.List;
import java.util.Optional;

public interface LotteryTrialFacade {

    List<LotteryTrialDo> getLotteryTrialList(LotteryEnum type, Integer size);

    LotteryTrialDo getLatestLotteryTrial(LotteryEnum type);

    Optional<LotteryTrialDo> getLotteryTrial(LotteryEnum type, String period);

}
