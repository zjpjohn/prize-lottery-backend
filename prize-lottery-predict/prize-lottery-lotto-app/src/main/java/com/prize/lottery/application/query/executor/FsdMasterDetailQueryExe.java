package com.prize.lottery.application.query.executor;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.mapper.Fc3dIcaiMapper;
import com.prize.lottery.mapper.Fc3dMasterMapper;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.po.master.MasterSubscribePo;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.fc3d.Fc3dMasterDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FsdMasterDetailQueryExe {

    private final Fc3dIcaiMapper    fc3dIcaiMapper;
    private final Fc3dMasterMapper  fc3dMasterMapper;
    private final LotteryInfoMapper lotteryInfoMapper;
    private final MasterInfoMapper  masterInfoMapper;

    /**
     * 福彩3D专家详情
     *
     * @param masterId 专家标识
     * @param userId   用户标识
     */
    public Fc3dMasterDetail execute(String masterId, Long userId) {
        Fc3dMasterDetail detail = fc3dMasterMapper.getFc3dMasterDetail(masterId);
        Assert.notNull(detail, ResponseHandler.MASTER_NONE);
        //预测数据是否已更新
        String lottoPeriod = lotteryInfoMapper.latestPeriod(LotteryEnum.FC3D.getType());
        Period period      = fc3dIcaiMapper.latestMasterFc3dPeriod(masterId);
        if (period != null) {
            //设置是否更新
            detail.setModified(lottoPeriod.compareTo(period.getPeriod()) < 0 ? 1 : 0);
            //设置vip标识
            detail.setVip(fc3dMasterMapper.isFc3dVipMaster(masterId, period.getPeriod()));
        }
        MasterSubscribePo subscribe = masterInfoMapper.getMasterSubscribeUk(userId, masterId, LotteryEnum.FC3D.getType());
        if (subscribe != null) {
            //设置订阅标识
            detail.setSubscribed(1);
            detail.setTrace(subscribe.getTrace());
            detail.setTraceZh(subscribe.getTraceZh());
            detail.setSpecial(subscribe.getSpecial());
        }
        return detail;
    }
}
