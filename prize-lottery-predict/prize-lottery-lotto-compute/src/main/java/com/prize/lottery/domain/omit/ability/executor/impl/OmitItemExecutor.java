package com.prize.lottery.domain.omit.ability.executor.impl;

import com.prize.lottery.domain.omit.ability.LotteryN3ItemAbility;
import com.prize.lottery.domain.omit.ability.LotteryP5OmitAbility;
import com.prize.lottery.domain.omit.ability.executor.ItemExecutor;
import com.prize.lottery.enums.LotteryEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OmitItemExecutor implements ItemExecutor {

    private final LotteryN3ItemAbility n3ItemAbility;
    private final LotteryP5OmitAbility p5OmitAbility;

    @Override
    public void load(LotteryEnum type) {
        if (type == LotteryEnum.PL5) {
            p5OmitAbility.load();
            return;
        }
        n3ItemAbility.load(type);
    }

    @Override
    public void initialize(LotteryEnum type) {
        if (type == LotteryEnum.PL5) {
            p5OmitAbility.initialize();
            return;
        }
        n3ItemAbility.initialize(type);
    }

    @Override
    public void nextOmit(String period, LotteryEnum type) {
        if (type == LotteryEnum.PL5) {
            p5OmitAbility.nextOmit(period);
            return;
        }
        n3ItemAbility.nextOmit(period, type);
    }

}
