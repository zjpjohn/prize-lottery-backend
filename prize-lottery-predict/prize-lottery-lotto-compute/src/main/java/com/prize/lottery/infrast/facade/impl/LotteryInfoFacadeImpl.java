package com.prize.lottery.infrast.facade.impl;

import com.cloud.arch.utils.CollectionUtils;
import com.prize.lottery.domain.lottery.facade.LotteryInfoFacade;
import com.prize.lottery.domain.lottery.model.LotteryInfoDo;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.utils.LotteryApiEnum;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LotteryInfoFacadeImpl implements LotteryInfoFacade {

    @Override
    public List<LotteryInfoDo> getLotteryList(LotteryEnum type, Integer size) {
        LotteryApiEnum apiEnum = LotteryApiEnum.valueOf(type);
        return apiEnum.fetch(size);
    }

    @Override
    public List<LotteryInfoDo> getLotteryList(LotteryEnum type, String start, String end) {
        return LotteryApiEnum.valueOf(type).fetch(start, end);
    }

    @Override
    public LotteryInfoDo getLatestLotteryInfo(LotteryEnum type) {
        List<LotteryInfoDo> lotteryList = this.getLotteryList(type, 1);
        if (CollectionUtils.isEmpty(lotteryList)) {
            return null;
        }
        return lotteryList.get(0);
    }

}
