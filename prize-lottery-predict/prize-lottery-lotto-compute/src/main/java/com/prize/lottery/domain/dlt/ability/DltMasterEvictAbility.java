package com.prize.lottery.domain.dlt.ability;

import com.cloud.arch.utils.CollectionUtils;
import com.prize.lottery.domain.IMasterEvictAbility;
import com.prize.lottery.dto.WorseMasterFilter;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.DltIcaiMapper;
import com.prize.lottery.mapper.DltMasterMapper;
import com.prize.lottery.mapper.MasterInfoMapper;
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
public class DltMasterEvictAbility implements IMasterEvictAbility {

    private final DltIcaiMapper    icaiMapper;
    private final DltMasterMapper  dltMapper;
    private final MasterInfoMapper masterMapper;

    @Override
    public LotteryEnum bizIndex() {
        return LotteryEnum.DLT;
    }

    @Override
    public void extractMasters() {
        String period = dltMapper.latestDltRankPeriod();
        List<String> periods =
            IntStream.range(0, 8).mapToObj(i -> PeriodCalculator.dltPeriod(period, i)).collect(Collectors.toList());
        WorseMasterFilter filter  = WorseMasterFilter.dltFilter(periods);
        List<String>      masters = dltMapper.getWorseMasters(filter);
        if (CollectionUtils.isNotEmpty(masters)) {
            List<MasterEvictPo> evictList = CollectionUtils.toList(masters, MasterEvictPo::dlt);
            masterMapper.addMasterEvicts(evictList);
            masterMapper.delLotteryMasters(LotteryEnum.DLT, masters);
        }
    }

    @Override
    public void clearForecasts() {
        List<MasterEvictPo> evicts = masterMapper.getMasterEvicts(LotteryEnum.DLT);
        if (CollectionUtils.isNotEmpty(evicts)) {
            List<String> masters = CollectionUtils.toList(evicts, MasterEvictPo::getMasterId);
            icaiMapper.delDataList(masters);
            dltMapper.delMasterRates(masters);
        }
    }

    @Override
    public void clearMasters() {
        List<MasterEvictPo> evicts = masterMapper.getMasterEvicts(LotteryEnum.DLT);
        if (CollectionUtils.isNotEmpty(evicts)) {
            List<String> masters = CollectionUtils.toList(evicts, MasterEvictPo::getMasterId);
            masterMapper.delMasterSubscribes(LotteryEnum.DLT, masters);
            masterMapper.delMasterBrowses(LotteryEnum.DLT, masters);
            masterMapper.delMasterEvicts(LotteryEnum.DLT);
        }
    }

}
