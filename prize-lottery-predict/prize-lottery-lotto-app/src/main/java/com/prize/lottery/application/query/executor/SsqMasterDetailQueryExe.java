package com.prize.lottery.application.query.executor;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.mapper.SsqIcaiMapper;
import com.prize.lottery.mapper.SsqMasterMapper;
import com.prize.lottery.po.master.MasterSubscribePo;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.ssq.SsqMasterDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SsqMasterDetailQueryExe {

    private final SsqIcaiMapper     ssqIcaiMapper;
    private final SsqMasterMapper   ssqMasterMapper;
    private final LotteryInfoMapper lotteryInfoMapper;
    private final MasterInfoMapper  masterInfoMapper;

    /**
     * 查询双色球专家详情
     *
     * @param masterId 专家标识
     * @param userId   用户标识
     */
    public SsqMasterDetail execute(String masterId, Long userId) {
        SsqMasterDetail detail = ssqMasterMapper.getSsqMasterDetail(masterId);
        Assert.notNull(detail, ResponseHandler.MASTER_NONE);
        //预测数据是否已更新
        String lottoPeriod = lotteryInfoMapper.latestPeriod(LotteryEnum.SSQ.getType());
        Period period      = ssqIcaiMapper.latestSsqMasterPeriod(masterId);
        if (period != null) {
            //设置是否更新数据
            detail.setModified(lottoPeriod.compareTo(period.getPeriod()) < 0 ? 1 : 0);
            //设置vip标识
            detail.setVip(ssqMasterMapper.isSsqVipMaster(masterId, period.getPeriod()));
        }
        //设置订阅关注信息
        MasterSubscribePo subscribe = masterInfoMapper.getMasterSubscribeUk(userId, masterId, LotteryEnum.SSQ.getType());
        if (subscribe != null) {
            detail.setSubscribed(1);
            detail.setTrace(subscribe.getTrace());
            detail.setTraceZh(subscribe.getTraceZh());
            detail.setSpecial(subscribe.getSpecial());
        }
        return detail;
    }
}
