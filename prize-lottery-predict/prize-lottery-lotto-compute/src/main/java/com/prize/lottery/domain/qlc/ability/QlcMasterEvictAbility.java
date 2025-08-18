package com.prize.lottery.domain.qlc.ability;

import com.cloud.arch.utils.CollectionUtils;
import com.prize.lottery.domain.IMasterEvictAbility;
import com.prize.lottery.dto.WorseMasterFilter;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.mapper.QlcIcaiMapper;
import com.prize.lottery.mapper.QlcMasterMapper;
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
public class QlcMasterEvictAbility implements IMasterEvictAbility {

    private final QlcIcaiMapper    icaiMapper;
    private final QlcMasterMapper  qlcMapper;
    private final MasterInfoMapper masterMapper;

    @Override
    public LotteryEnum bizIndex() {
        return LotteryEnum.QLC;
    }

    @Override
    public void extractMasters() {
        String period = qlcMapper.latestQlcRankPeriod();
        List<String> periods =
            IntStream.range(0, 8).mapToObj(i -> PeriodCalculator.qlcPeriod(period, i)).collect(Collectors.toList());
        WorseMasterFilter filter  = WorseMasterFilter.qlcFilter(periods);
        List<String>      masters = qlcMapper.getWorseMasters(filter);
        if (CollectionUtils.isNotEmpty(masters)) {
            List<MasterEvictPo> evictList = CollectionUtils.toList(masters, MasterEvictPo::qlc);
            masterMapper.addMasterEvicts(evictList);
            masterMapper.delLotteryMasters(LotteryEnum.QLC, masters);
        }
    }

    @Override
    public void clearForecasts() {
        List<MasterEvictPo> evicts = masterMapper.getMasterEvicts(LotteryEnum.QLC);
        if (CollectionUtils.isNotEmpty(evicts)) {
            List<String> masters = CollectionUtils.toList(evicts, MasterEvictPo::getMasterId);
            icaiMapper.delDataList(masters);
            qlcMapper.delMasterRates(masters);
        }
    }

    @Override
    public void clearMasters() {
        List<MasterEvictPo> evicts = masterMapper.getMasterEvicts(LotteryEnum.QLC);
        if (CollectionUtils.isNotEmpty(evicts)) {
            List<String> masters = CollectionUtils.toList(evicts, MasterEvictPo::getMasterId);
            masterMapper.delMasterSubscribes(LotteryEnum.QLC, masters);
            masterMapper.delMasterBrowses(LotteryEnum.QLC, masters);
            masterMapper.delMasterEvicts(LotteryEnum.QLC);
        }
    }

}
