package com.prize.lottery.application.service.impl;

import com.prize.lottery.application.service.ILotteryIndexService;
import com.prize.lottery.domain.index.ability.IndexFc3dAbility;
import com.prize.lottery.domain.index.ability.IndexPl3Ability;
import com.prize.lottery.value.BallIndex;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LotteryIndexService implements ILotteryIndexService {

    private final IndexFc3dAbility fc3dAbility;
    private final IndexPl3Ability  pl3Ability;

    @Override
    public List<BallIndex> fc3dFullIndex(String period) {
        return fc3dAbility.fullBallIndex(period);
    }

    @Override
    public List<BallIndex> fc3dVipIndex(String period) {
        return fc3dAbility.vipBallIndex(period);
    }

    @Override
    public void fc3dLottoIndex(String period) {
        fc3dAbility.calcIndex(period);
    }

    @Override
    public void fc3dItemIndex(String period) {
        fc3dAbility.calcItemIndex(period);
    }

    @Override
    public void pl3LottoIndex(String period) {
        pl3Ability.calcIndex(period);
    }

    @Override
    public void pl3ItemIndex(String period) {
        pl3Ability.calcItemIndex(period);
    }

    @Override
    public List<BallIndex> pl3FullIndex(String period) {
        return pl3Ability.fullBallIndex(period);
    }

    @Override
    public List<BallIndex> pl3VipIndex(String period) {
        return pl3Ability.vipBallIndex(period);
    }
}
