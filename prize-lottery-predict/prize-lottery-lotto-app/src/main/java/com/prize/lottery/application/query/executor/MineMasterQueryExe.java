package com.prize.lottery.application.query.executor;

import com.prize.lottery.api.IUserInfoService;
import com.prize.lottery.application.assembler.MasterInfoAssembler;
import com.prize.lottery.application.vo.MasterInfoDetailVo;
import com.prize.lottery.dto.ExpertAcctRepo;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.po.master.MasterInfoPo;
import com.prize.lottery.po.master.MasterLotteryPo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class MineMasterQueryExe {

    @Resource
    private MasterInfoMapper    masterInfoMapper;
    @Resource
    private MasterInfoAssembler masterInfoAssembler;
    @DubboReference
    private IUserInfoService    userInfoService;

    public MasterInfoDetailVo query(Long userId) {
        //专家账户
        ExpertAcctRepo expert = userInfoService.getExpertUser(userId);
        //专家信息
        MasterInfoPo          master    = masterInfoMapper.getMasterInfoBySeq(expert.getMasterId(), true);
        List<MasterLotteryPo> lotteries = masterInfoMapper.getMasterEnabledLotteries(expert.getMasterId());

        return masterInfoAssembler.toVo(master, lotteries, 0);
    }
}
