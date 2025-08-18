package com.prize.lottery.application.query.executor;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.Kl8IcaiMapper;
import com.prize.lottery.mapper.Kl8MasterMapper;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.po.master.MasterSubscribePo;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.kl8.Kl8MasterDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Kl8MasterDetailQueryExe {

    private final Kl8IcaiMapper     kl8IcaiMapper;
    private final Kl8MasterMapper   kl8MasterMapper;
    private final LotteryInfoMapper lotteryInfoMapper;
    private final MasterInfoMapper  masterInfoMapper;

    /**
     * 查看预测专家详情
     *
     * @param masterId 专家标识
     * @param userId   用户标识
     */
    public Kl8MasterDetail execute(String masterId, Long userId) {
        //专家信息
        Kl8MasterDetail detail = Assert.notNull(kl8MasterMapper.getKl8MasterDetail(masterId), ResponseHandler.MASTER_NONE);

        //是否已更新预测
        String lottoPeriod = lotteryInfoMapper.latestPeriod(LotteryEnum.KL8.getType());
        Period period      = kl8IcaiMapper.latestKl8MasterPeriod(masterId);
        if (lottoPeriod.compareTo(period.getPeriod()) <= 0) {
            detail.setModified(0);
        }

        //用户是否已订阅专家
        MasterSubscribePo subscribePo = new MasterSubscribePo(userId, masterId, LotteryEnum.KL8);
        detail.setSubscribed(masterInfoMapper.hasSubscribeMaster(subscribePo));

        return detail;
    }

}
