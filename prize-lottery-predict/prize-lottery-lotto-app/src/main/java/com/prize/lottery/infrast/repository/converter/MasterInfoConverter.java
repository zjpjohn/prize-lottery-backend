package com.prize.lottery.infrast.repository.converter;

import com.prize.lottery.domain.browse.model.ForecastBrowse;
import com.prize.lottery.domain.master.model.MasterAccumulate;
import com.prize.lottery.domain.master.model.MasterInfo;
import com.prize.lottery.domain.master.model.MasterLottery;
import com.prize.lottery.domain.user.model.UserFocus;
import com.prize.lottery.domain.user.model.UserSubscribe;
import com.prize.lottery.po.master.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MasterInfoConverter {

    MasterInfoPo toPo(MasterInfo masterInfo);

    @Mapping(source = "masterId", target = "seq")
    MasterInfoPo toPo(MasterAccumulate accumulate);

    List<MasterInfoPo> toMasters(List<MasterAccumulate> accumulates);

    MasterInfo toDo(MasterInfoPo masterInfo);

    MasterLotteryPo toPo(MasterLottery masterLottery);

    List<MasterLotteryPo> toList(List<MasterLottery> lotteries);

    MasterLottery toDo(MasterLotteryPo masterLottery);

    UserSubscribe toDo(MasterSubscribePo subscribe);

    MasterSubscribePo toPo(UserSubscribe subscribe);

    List<MasterSubscribePo> toPos(List<UserSubscribe> subscribes);

    MasterFocusPo toPo(UserFocus focus);

    UserFocus toDo(MasterFocusPo focus);

    @Mapping(source = "period.period", target = "period")
    MasterBrowsePo toPo(ForecastBrowse browse);
}
