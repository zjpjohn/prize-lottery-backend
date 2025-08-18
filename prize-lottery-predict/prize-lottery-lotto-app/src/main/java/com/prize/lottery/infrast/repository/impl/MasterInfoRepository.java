package com.prize.lottery.infrast.repository.impl;

import com.cloud.arch.web.error.ApiBizException;
import com.prize.lottery.domain.master.model.MasterInfo;
import com.prize.lottery.domain.master.model.MasterLottery;
import com.prize.lottery.domain.master.repository.IMasterInfoRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.repository.converter.MasterInfoConverter;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.po.master.MasterInfoPo;
import com.prize.lottery.po.master.MasterLotteryPo;
import com.prize.lottery.value.MasterValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MasterInfoRepository implements IMasterInfoRepository {

    private final MasterInfoMapper    masterInfoMapper;
    private final MasterInfoConverter masterInfoConverter;

    @Override
    public void saveMasterInfo(MasterInfo masterInfo) {
        List<MasterLottery> lotteries = masterInfo.getLotteries();
        if (!CollectionUtils.isEmpty(lotteries)) {
            List<MasterLotteryPo> lotteryList = masterInfoConverter.toList(lotteries);
            masterInfoMapper.addMasterLotteries(lotteryList);
        }
        MasterInfoPo master = masterInfoConverter.toPo(masterInfo);
        if (master.getId() != null) {
            masterInfoMapper.editMasterInfo(master);
            return;
        }
        masterInfoMapper.addMasterInfo(master);
    }

    @Override
    public MasterInfo getMasterInfo(String masterId) {
        return Optional.ofNullable(masterInfoMapper.getMasterInfoBySeq(masterId, false))
                       .map(masterInfoConverter::toDo)
                       .orElseThrow(() -> new ApiBizException(404, "专家不存在."));
    }

    @Override
    public MasterValue ofMaster(String masterId) {
        return masterInfoMapper.getMasterValue(masterId);
    }

    @Override
    public MasterLottery getMasterLottery(LotteryEnum type, String masterId) {
        MasterLotteryPo lottery = masterInfoMapper.getMasterLotteryByUk(type.getType(), masterId);
        return Optional.ofNullable(lottery).map(masterInfoConverter::toDo).orElse(null);
    }
}
