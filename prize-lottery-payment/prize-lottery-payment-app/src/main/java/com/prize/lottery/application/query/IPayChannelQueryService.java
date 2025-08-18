package com.prize.lottery.application.query;


import com.prize.lottery.application.query.vo.PayChannelVo;
import com.prize.lottery.infrast.persist.po.PayChannelPo;

import java.util.List;

public interface IPayChannelQueryService {

    PayChannelPo getPayChannel(Long id);

    List<PayChannelPo> getAllPayChannels();

    List<PayChannelVo> getPayChannels();

    List<PayChannelVo> getWithdrawChannels();

}
