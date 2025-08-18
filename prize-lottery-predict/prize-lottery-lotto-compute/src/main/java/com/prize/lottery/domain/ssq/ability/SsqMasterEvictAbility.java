package com.prize.lottery.domain.ssq.ability;

import com.cloud.arch.utils.CollectionUtils;
import com.prize.lottery.domain.IMasterEvictAbility;
import com.prize.lottery.dto.WorseMasterFilter;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.mapper.SsqIcaiMapper;
import com.prize.lottery.mapper.SsqMasterMapper;
import com.prize.lottery.po.master.MasterEvictPo;
import com.prize.lottery.utils.PeriodCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class SsqMasterEvictAbility implements IMasterEvictAbility {

    private final SsqIcaiMapper    icaiMapper;
    private final SsqMasterMapper  ssqMapper;
    private final MasterInfoMapper masterMapper;

    @Override
    public LotteryEnum bizIndex() {
        return LotteryEnum.SSQ;
    }

    @Override
    public void extractMasters() {
        String period = ssqMapper.latestSsqRankPeriod();
        List<String> periods = IntStream.range(0, 8)
                                        .mapToObj(i -> PeriodCalculator.ssqPeriod(period, i))
                                        .collect(Collectors.toList());
        WorseMasterFilter filter  = WorseMasterFilter.ssqFilter(periods);
        List<String>      masters = ssqMapper.getWorseMasters(filter);
        if (CollectionUtils.isNotEmpty(masters)) {
            List<MasterEvictPo> evictList = CollectionUtils.toList(masters, MasterEvictPo::ssq);
            masterMapper.addMasterEvicts(evictList);
            masterMapper.delLotteryMasters(LotteryEnum.SSQ, masters);
        }
    }

    @Override
    public void clearForecasts() {
        List<MasterEvictPo> evicts = masterMapper.getMasterEvicts(LotteryEnum.SSQ);
        if (CollectionUtils.isNotEmpty(evicts)) {
            List<String> masters = CollectionUtils.toList(evicts, MasterEvictPo::getMasterId);
            icaiMapper.delDataList(masters);
            ssqMapper.delMasterRates(masters);
        }
    }

    @Override
    public void clearMasters() {
        List<MasterEvictPo> evicts = masterMapper.getMasterEvicts(LotteryEnum.SSQ);
        if (CollectionUtils.isNotEmpty(evicts)) {
            List<String> masters = CollectionUtils.toList(evicts, MasterEvictPo::getMasterId);
            masterMapper.delMasterSubscribes(LotteryEnum.SSQ, masters);
            masterMapper.delMasterBrowses(LotteryEnum.SSQ, masters);
            masterMapper.delMasterEvicts(LotteryEnum.SSQ);
        }
    }

}
