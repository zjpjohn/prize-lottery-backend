package com.prize.lottery.infrast.spider.open.impl;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.SsqChannel;
import com.prize.lottery.infrast.spider.open.AbsOpenFetchHandler;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.mapper.SsqIcaiMapper;
import com.prize.lottery.po.master.MasterLotteryPo;
import com.prize.lottery.po.ssq.SsqIcaiPo;
import com.prize.lottery.value.ICaiForecast;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component("SsqOpenFetchHandler")
public class SsqOpenFetchHandler extends AbsOpenFetchHandler {

    private final SsqIcaiMapper ssqIcaiMapper;

    public SsqOpenFetchHandler(MasterInfoMapper masterInfoMapper, SsqIcaiMapper ssqIcaiMapper) {
        super(LotteryEnum.SSQ, masterInfoMapper);
        this.ssqIcaiMapper = ssqIcaiMapper;
    }

    @Transactional
    public void handle(ICaiForecast data) {
        super.handle(data);
        ssqIcaiMapper.addSsqICai(toConvert(data));
    }

    @Override
    public List<Pair<String, String>> masters(String period) {
        List<MasterLotteryPo> masters = ssqIcaiMapper.getAllNoneDataMasters(period);
        return masters.stream().map(e -> Pair.of(e.getMasterId(), e.getSourceId())).collect(Collectors.toList());
    }

    @Override
    public List<Pair<String, String>> incrMasters(String period) {
        List<MasterLotteryPo> masters = ssqIcaiMapper.getNoneDataMasters(period);
        return masters.stream().map(e -> Pair.of(e.getMasterId(), e.getSourceId())).collect(Collectors.toList());
    }

    private SsqIcaiPo toConvert(ICaiForecast data) {
        SsqIcaiPo po = new SsqIcaiPo();
        po.setPeriod(data.getPeriod());
        po.setMasterId(data.getMasterId());
        po.setGmtCreate(getRandomTime(7200000));
        Arrays.stream(SsqChannel.values()).forEach(channel -> channel.toConvert(po, data));
        return po;
    }

}
