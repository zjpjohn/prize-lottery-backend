package com.prize.lottery.application.query.impl;

import com.prize.lottery.application.assembler.PayChannelAssembler;
import com.prize.lottery.application.query.IPayChannelQueryService;
import com.prize.lottery.application.query.vo.PayChannelVo;
import com.prize.lottery.infrast.persist.mapper.PayChannelMapper;
import com.prize.lottery.infrast.persist.po.PayChannelPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayChannelQueryService implements IPayChannelQueryService {

    private final PayChannelMapper    payChannelMapper;
    private final PayChannelAssembler payChannelAssembler;

    @Override
    public PayChannelPo getPayChannel(Long id) {
        return payChannelMapper.getPayChannelById(id);
    }

    @Override
    public List<PayChannelPo> getAllPayChannels() {
        return payChannelMapper.getAllChannelList();
    }

    @Override
    public List<PayChannelVo> getPayChannels() {
        List<PayChannelPo> channels = payChannelMapper.getPayChannelList();
        return payChannelAssembler.toVoList(channels);
    }

    @Override
    public List<PayChannelVo> getWithdrawChannels() {
        List<PayChannelPo> channels = payChannelMapper.getWithdrawChannelList();
        return payChannelAssembler.toVoList(channels);
    }


}
