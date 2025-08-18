package com.prize.lottery.domain.omit.ability;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.model.LottoPianOmitDo;
import com.prize.lottery.domain.omit.repository.ILottoPianOmitRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryOmitPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LottoN3PianAbility {

    private final LotteryInfoMapper        mapper;
    private final ILottoPianOmitRepository repository;

    public void initialize(LotteryEnum type, Integer limit) {
        int exist = mapper.hasExistPianOmit(type);
        Assert.state(exist <= 0, ResponseHandler.HAS_INIT_DATA);
        List<LotteryOmitPo> omits = mapper.getLotteryOmits(type, limit);
        if (CollectionUtils.isEmpty(omits)) {
            return;
        }
        omits.sort(Comparator.comparing(LotteryOmitPo::getPeriod));
        List<LottoPianOmitDo> pianList = Lists.newArrayList();
        for (int i = 9; i < omits.size(); i++) {
            List<LotteryOmitPo>   omitList    = omits.subList(i - 9, i + 1);
            LotteryOmitPo         lotteryOmit = omitList.get(omitList.size() - 1);
            List<LottoPianOmitDo> doList      = LottoPianOmitDo.allLevelOmits(type, lotteryOmit.getPeriod(), omitList);
            pianList.addAll(doList);
        }
        repository.saveBatch(pianList);
    }

    public void execute(LotteryEnum type, String period) {
        List<LotteryOmitPo> omits = mapper.getLotteryOmitsLtPeriod(type, period, 11);
        if (CollectionUtils.isEmpty(omits) || omits.size() < 10) {
            return;
        }
        omits.sort(Comparator.comparing(LotteryOmitPo::getPeriod));
        List<LottoPianOmitDo> omitList = LottoPianOmitDo.allLevelOmits(type, period, omits);
        repository.saveBatch(omitList);
    }

}
