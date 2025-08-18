package com.prize.lottery.application.query.executor;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.application.assembler.MasterInfoAssembler;
import com.prize.lottery.application.vo.MasterInfoDetailVo;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.po.master.MasterInfoPo;
import com.prize.lottery.po.master.MasterLotteryPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MasterDetailQueryExe {

    private final MasterInfoMapper    masterInfoMapper;
    private final MasterInfoAssembler masterInfoAssembler;

    public MasterInfoDetailVo query(String masterId, Long userId, Integer search) {
        //专家信息
        MasterInfoPo master = masterInfoMapper.getMasterInfoBySeq(masterId, true);
        Assert.notNull(master, ResponseHandler.MASTER_NONE);
        //专家开通彩种
        List<MasterLotteryPo> lotteries = masterInfoMapper.getMasterEnabledLotteries(masterId);
        //用户是否已关注专家
        int focused = masterInfoMapper.hasFocusMaster(userId, masterId);

        //搜索进入
        if (search == 1) {
            MasterInfoPo masterInfo = new MasterInfoPo();
            masterInfo.setSeq(masterId);
            masterInfo.setSearches(1);
            masterInfoMapper.accumBatchMasters(Lists.newArrayList(masterInfo));
        }

        return masterInfoAssembler.toVo(master, lotteries, focused);
    }
}
