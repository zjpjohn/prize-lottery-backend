package com.prize.lottery.application.query.executor;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.mapper.Pl3IcaiMapper;
import com.prize.lottery.mapper.Pl3MasterMapper;
import com.prize.lottery.po.master.MasterSubscribePo;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.pl3.Pl3MasterDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlsMasterDetailQueryExe {

    private final Pl3IcaiMapper     pl3IcaiMapper;
    private final Pl3MasterMapper   pl3MasterMapper;
    private final LotteryInfoMapper lotteryInfoMapper;
    private final MasterInfoMapper  masterInfoMapper;

    /**
     * 排列三专家详情
     *
     * @param masterId 专家标识
     * @param userId   用户标识
     */
    public Pl3MasterDetail execute(String masterId, Long userId) {
        Pl3MasterDetail detail = pl3MasterMapper.getPl3MasterDetail(masterId);
        Assert.notNull(detail, ResponseHandler.MASTER_NONE);
        //预测数据是否已更新
        String lottoPeriod = lotteryInfoMapper.latestPeriod(LotteryEnum.PL3.getType());
        Period period      = pl3IcaiMapper.latestMasterPl3Period(masterId);
        if (period != null) {
            //设置专家是否更新
            detail.setModified(lottoPeriod.compareTo(period.getPeriod()) < 0 ? 1 : 0);
            //设置vip标识
            detail.setVip(pl3MasterMapper.isPl3VipMaster(masterId, period.getPeriod()));
        }
        //设置订阅关注信息
        MasterSubscribePo subscribe = masterInfoMapper.getMasterSubscribeUk(userId, masterId, LotteryEnum.PL3.getType());
        if (subscribe != null) {
            detail.setSubscribed(1);
            detail.setTrace(subscribe.getTrace());
            detail.setTraceZh(subscribe.getTraceZh());
            detail.setSpecial(subscribe.getSpecial());
        }
        return detail;
    }
}
