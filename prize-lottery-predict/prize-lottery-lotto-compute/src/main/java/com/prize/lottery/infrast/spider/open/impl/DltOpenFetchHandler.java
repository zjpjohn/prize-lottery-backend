package com.prize.lottery.infrast.spider.open.impl;

import com.prize.lottery.enums.DltChannel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.spider.open.AbsOpenFetchHandler;
import com.prize.lottery.mapper.DltIcaiMapper;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.po.dlt.DltIcaiPo;
import com.prize.lottery.po.master.MasterLotteryPo;
import com.prize.lottery.value.ICaiForecast;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component("DltOpenFetchHandler")
public class DltOpenFetchHandler extends AbsOpenFetchHandler {

    private final DltIcaiMapper dltIcaiMapper;

    public DltOpenFetchHandler(MasterInfoMapper masterInfoMapper, DltIcaiMapper dltIcaiMapper) {
        super(LotteryEnum.DLT, masterInfoMapper);
        this.dltIcaiMapper = dltIcaiMapper;
    }

    @Override
    @Transactional
    public void handle(ICaiForecast data) {
        super.handle(data);
        dltIcaiMapper.addDltICai(toConvert(data));
    }

    @Override
    public List<Pair<String, String>> masters(String period) {
        List<MasterLotteryPo> masters = dltIcaiMapper.getAllNoneDataMasters(period);
        return masters.stream().map(e -> Pair.of(e.getMasterId(), e.getSourceId())).collect(Collectors.toList());
    }

    @Override
    public List<Pair<String, String>> incrMasters(String period) {
        List<MasterLotteryPo> masters = dltIcaiMapper.getNoneDataMasters(period);
        return masters.stream().map(e -> Pair.of(e.getMasterId(), e.getSourceId())).collect(Collectors.toList());
    }

    private DltIcaiPo toConvert(ICaiForecast data) {
        DltIcaiPo po = new DltIcaiPo();
        po.setMasterId(data.getMasterId());
        po.setPeriod(data.getPeriod());
        po.setGmtCreate(getRandomTime(7200000));
        Arrays.stream(DltChannel.values()).forEach(channel -> channel.toConvert(po, data));
        return po;
    }

}
