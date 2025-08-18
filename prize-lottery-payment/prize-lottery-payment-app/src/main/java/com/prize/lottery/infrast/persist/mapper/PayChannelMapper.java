package com.prize.lottery.infrast.persist.mapper;

import com.prize.lottery.infrast.persist.po.PayChannelPo;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PayChannelMapper {

    int addPayChannel(PayChannelPo channel);

    int editPayChannel(PayChannelPo channel);

    PayChannelPo getPayChannelById(Long id);

    PayChannelPo getPayChannel(String channel);

    List<PayChannelPo> getPayChannelList();

    List<PayChannelPo> getAllChannelList();

    List<PayChannelPo> getWithdrawChannelList();

}
