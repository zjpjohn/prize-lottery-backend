package com.prize.lottery.domain.fc3d.ability;

import com.cloud.arch.utils.CollectionUtils;
import com.prize.lottery.domain.IMasterEvictAbility;
import com.prize.lottery.dto.WorseMasterFilter;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.Fc3dIcaiMapper;
import com.prize.lottery.mapper.Fc3dMasterMapper;
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
public class Fc3dMasterEvictAbility implements IMasterEvictAbility {

    private final Fc3dIcaiMapper   icaiMapper;
    private final Fc3dMasterMapper fc3dMapper;
    private final MasterInfoMapper masterMapper;

    @Override
    public LotteryEnum bizIndex() {
        return LotteryEnum.FC3D;
    }

    @Override
    public void extractMasters() {
        String period = fc3dMapper.latestFc3dRankPeriod();
        List<String> periods =
            IntStream.range(0, 6).mapToObj(i -> PeriodCalculator.fc3dPeriod(period, i)).collect(Collectors.toList());
        WorseMasterFilter filter  = WorseMasterFilter.n3Filter(periods);
        List<String>      masters = fc3dMapper.getWorseMasters(filter);
        if (CollectionUtils.isNotEmpty(masters)) {
            List<MasterEvictPo> masterEvicts = CollectionUtils.toList(masters, MasterEvictPo::fc3d);
            //保存淘汰专家列表
            masterMapper.addMasterEvicts(masterEvicts);
            //解绑专家猜中映射
            masterMapper.delLotteryMasters(LotteryEnum.FC3D, masters);
        }
    }

    @Override
    public void clearForecasts() {
        List<MasterEvictPo> evicts = masterMapper.getMasterEvicts(LotteryEnum.FC3D);
        if (CollectionUtils.isNotEmpty(evicts)) {
            List<String> masterIds = CollectionUtils.toList(evicts, MasterEvictPo::getMasterId);
            icaiMapper.delDataList(masterIds);
            fc3dMapper.delMasterRates(masterIds);
        }
    }

    @Override
    public void clearMasters() {
        List<MasterEvictPo> evicts = masterMapper.getMasterEvicts(LotteryEnum.FC3D);
        if (CollectionUtils.isNotEmpty(evicts)) {
            List<String> masterIds = CollectionUtils.toList(evicts, MasterEvictPo::getMasterId);
            masterMapper.delMasterBrowses(LotteryEnum.FC3D, masterIds);
            masterMapper.delMasterSubscribes(LotteryEnum.FC3D, masterIds);
            masterMapper.delMasterEvicts(LotteryEnum.FC3D);
        }
    }

}
