package com.prize.lottery.domain.pl3.ability;

import com.cloud.arch.utils.CollectionUtils;
import com.prize.lottery.domain.IMasterEvictAbility;
import com.prize.lottery.dto.WorseMasterFilter;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.mapper.Pl3IcaiMapper;
import com.prize.lottery.mapper.Pl3MasterMapper;
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
public class Pl3MasterEvictAbility implements IMasterEvictAbility {

    private final Pl3IcaiMapper    icaiMapper;
    private final Pl3MasterMapper  pl3Mapper;
    private final MasterInfoMapper masterMapper;

    @Override
    public LotteryEnum bizIndex() {
        return LotteryEnum.PL3;
    }

    @Override
    public void extractMasters() {
        String period = pl3Mapper.latestPl3RankPeriod();
        List<String> periods =
            IntStream.range(0, 6).mapToObj(i -> PeriodCalculator.pl3Period(period, i)).collect(Collectors.toList());
        WorseMasterFilter filter  = WorseMasterFilter.n3Filter(periods);
        List<String>      masters = pl3Mapper.getWorseMasters(filter);
        if (CollectionUtils.isNotEmpty(masters)) {
            List<MasterEvictPo> evictList = CollectionUtils.toList(masters, MasterEvictPo::pl3);
            masterMapper.addMasterEvicts(evictList);
            masterMapper.delLotteryMasters(LotteryEnum.PL3, masters);
        }
    }

    @Override
    public void clearForecasts() {
        List<MasterEvictPo> evicts = masterMapper.getMasterEvicts(LotteryEnum.PL3);
        if (CollectionUtils.isNotEmpty(evicts)) {
            List<String> masterIds = CollectionUtils.toList(evicts, MasterEvictPo::getMasterId);
            icaiMapper.delDataList(masterIds);
            pl3Mapper.delMasterRates(masterIds);
        }
    }

    @Override
    public void clearMasters() {
        List<MasterEvictPo> evicts = masterMapper.getMasterEvicts(LotteryEnum.PL3);
        if (CollectionUtils.isNotEmpty(evicts)) {
            List<String> masterIds = CollectionUtils.toList(evicts, MasterEvictPo::getMasterId);
            masterMapper.delMasterBrowses(LotteryEnum.PL3, masterIds);
            masterMapper.delMasterSubscribes(LotteryEnum.PL3, masterIds);
            masterMapper.delMasterEvicts(LotteryEnum.PL3);
        }
    }

}
