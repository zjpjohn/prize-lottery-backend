package com.prize.lottery.infrast.repository.impl;

import com.google.common.collect.Lists;
import com.prize.lottery.domain.master.model.MasterAccumulate;
import com.prize.lottery.domain.user.model.UserSubscribe;
import com.prize.lottery.domain.user.repository.IUserMasterRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.repository.converter.MasterInfoConverter;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.po.master.MasterInfoPo;
import com.prize.lottery.po.master.MasterSubscribePo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMasterRepository implements IUserMasterRepository {

    private final MasterInfoMapper    masterInfoMapper;
    private final MasterInfoConverter masterInfoConverter;

    @Override
    public void save(UserSubscribe subscribe) {
        MasterAccumulate accumulate = subscribe.getMasterAccumulate();
        if (accumulate != null) {
            MasterInfoPo masterInfo = masterInfoConverter.toPo(accumulate);
            masterInfoMapper.accumBatchMasters(Lists.newArrayList(masterInfo));
        }
        MasterSubscribePo subscribePo = masterInfoConverter.toPo(subscribe);
        if (subscribe.getId() == null) {
            masterInfoMapper.addMasterSubscribe(subscribePo);
            return;
        }
        masterInfoMapper.editMasterSubscribe(subscribePo);
    }

    @Override
    public void saveBatch(List<UserSubscribe> subscribes) {
        List<MasterSubscribePo> subscribePos = masterInfoConverter.toPos(subscribes);
        masterInfoMapper.addMasterSubscribes(subscribePos);

        List<MasterAccumulate> accumulates = subscribes.stream()
                                                       .map(UserSubscribe::getMasterAccumulate)
                                                       .collect(Collectors.toList());
        List<MasterInfoPo> masters = masterInfoConverter.toMasters(accumulates);
        masterInfoMapper.accumBatchMasters(masters);
    }

    @Override
    public UserSubscribe ofUk(Long userId, String masterId, LotteryEnum type) {
        return Optional.ofNullable(masterInfoMapper.getMasterSubscribeUk(userId, masterId, type.getType()))
                       .map(masterInfoConverter::toDo)
                       .orElseThrow(ResponseHandler.SUBSCRIBE_MASTER_NONE);
    }

    @Override
    public Boolean hasSubscribed(UserSubscribe subscribe) {
        MasterSubscribePo subscribePo = masterInfoConverter.toPo(subscribe);
        return masterInfoMapper.hasSubscribeMaster(subscribePo) == 0;
    }

    @Override
    public void removeSubscribe(UserSubscribe subscribe) {
        masterInfoMapper.delMasterSubscribe(subscribe.getId());
        MasterInfoPo master = masterInfoConverter.toPo(subscribe.getMasterAccumulate());
        masterInfoMapper.accumBatchMasters(Lists.newArrayList(master));
    }

}
