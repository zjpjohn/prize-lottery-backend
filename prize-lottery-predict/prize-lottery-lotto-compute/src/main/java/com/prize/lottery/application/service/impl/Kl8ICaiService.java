package com.prize.lottery.application.service.impl;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.service.IKl8ICaiService;
import com.prize.lottery.domain.ICaiDomainAbility;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.mapper.Kl8IcaiMapper;
import com.prize.lottery.mapper.Kl8MasterMapper;
import com.prize.lottery.mapper.LotteryInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class Kl8ICaiService implements IKl8ICaiService {

    private final Kl8IcaiMapper     kl8IcaiMapper;
    private final Kl8MasterMapper   kl8MasterMapper;
    private final LotteryInfoMapper lotteryInfoMapper;
    private final ICaiDomainAbility kl8ICaiDomainAbility;

    /**
     * 初始化计算历史预测数据命中
     */
    @Override
    @Transactional
    public void initCalcKl8Hit() {
        String latest = lotteryInfoMapper.latestPeriod(LotteryEnum.KL8.getType());
        Assert.state(StringUtils.isNotBlank(latest), ResponseHandler.NO_OPEN_LOTTERY);
        //获取全部未计算的历史数据
        List<String> periods = kl8IcaiMapper.getUnCalculatedPeriods(latest);
        Assert.state(!CollectionUtils.isEmpty(periods), ResponseHandler.NO_CALCULATE_DATA);
        //分期计算命中信息
        periods.forEach(kl8ICaiDomainAbility::calcForecastHit);
    }

    /**
     * 初始化计算专家历史命中率
     */
    @Override
    @Transactional
    public void initCalcMasterRate() {
        List<String> periods = kl8IcaiMapper.getUnCalcRatedPeriod();
        Assert.state(!CollectionUtils.isEmpty(periods), ResponseHandler.NO_CALCULATE_DATA);
        periods.forEach(kl8ICaiDomainAbility::calcMasterRate);
    }

    /**
     * 初始化计算历史专家排名
     */
    @Override
    @Transactional
    public void initCalcMasterRank() {
        List<String> periods = kl8MasterMapper.getUnRankedMasterPeriods();
        Assert.state(!CollectionUtils.isEmpty(periods), ResponseHandler.NO_CALCULATE_DATA);
        periods.forEach(kl8ICaiDomainAbility::calcMasterRank);
    }

}
