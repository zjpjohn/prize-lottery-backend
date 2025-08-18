package com.prize.lottery.application.query.executor;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.DltIcaiMapper;
import com.prize.lottery.mapper.DltMasterMapper;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.po.master.MasterSubscribePo;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.dlt.DltMasterDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DltMasterDetailQueryExe {

    private final DltIcaiMapper     dltIcaiMapper;
    private final DltMasterMapper   dltMasterMapper;
    private final LotteryInfoMapper lotteryInfoMapper;
    private final MasterInfoMapper  masterInfoMapper;

    /**
     * 查询大乐透专家详情
     *
     * @param masterId 专家标识
     * @param userId   用户标识
     */
    public DltMasterDetail execute(String masterId, Long userId) {
        DltMasterDetail detail = dltMasterMapper.getDltMasterDetail(masterId);
        Assert.notNull(detail, ResponseHandler.MASTER_NONE);
        //预测数据是否已更新
        String lottoPeriod = lotteryInfoMapper.latestPeriod(LotteryEnum.DLT.getType());
        Period period      = dltIcaiMapper.latestDltMasterPeriod(masterId);
        if (period != null) {
            //设置预测是否更新
            detail.setModified(lottoPeriod.compareTo(period.getPeriod()) < 0 ? 1 : 0);
            //设置vip标识
            detail.setVip(dltMasterMapper.isDltVipMaster(masterId, period.getPeriod()));
        }
        //设置订阅关注信息
        MasterSubscribePo subscribe = masterInfoMapper.getMasterSubscribeUk(userId, masterId, LotteryEnum.DLT.getType());
        if (subscribe != null) {
            detail.setSubscribed(1);
            detail.setTrace(subscribe.getTrace());
            detail.setTraceZh(subscribe.getTraceZh());
            detail.setSpecial(subscribe.getSpecial());
        }
        return detail;
    }

}
