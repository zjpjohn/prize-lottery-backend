package com.prize.lottery.infrast.facade.impl;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import com.prize.lottery.domain.lottery.facade.LotteryTrialFacade;
import com.prize.lottery.domain.lottery.model.LotteryTrialDo;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.utils.LotteryTrialEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LotteryTrailFacadeImpl implements LotteryTrialFacade {

    private static final RangeMap<Integer, Integer> sizeRange = TreeRangeMap.create();

    static {
        sizeRange.put(Range.openClosed(0, 30), 30);
        sizeRange.put(Range.openClosed(30, 100), 100);
        sizeRange.put(Range.greaterThan(100), 300);
    }

    @Override
    public List<LotteryTrialDo> getLotteryTrialList(LotteryEnum type, Integer size) {
        Integer batch = Assert.notNull(sizeRange.get(size), "this fetch size exception");
        return LotteryTrialEnum.valueOf(type).fetch(batch);
    }

    @Override
    public LotteryTrialDo getLatestLotteryTrial(LotteryEnum type) {
        List<LotteryTrialDo> list = this.getLotteryTrialList(type, 30);
        return list.get(0);
    }

    @Override
    public Optional<LotteryTrialDo> getLotteryTrial(LotteryEnum type, String period) {
        List<LotteryTrialDo> trials = this.getLotteryTrialList(type, 300);
        return trials.stream()
                     .filter(v -> v.getPeriod().equals(period))
                     .filter(v -> StringUtils.isNotBlank(v.getShi()))
                     .findFirst();
    }
}
