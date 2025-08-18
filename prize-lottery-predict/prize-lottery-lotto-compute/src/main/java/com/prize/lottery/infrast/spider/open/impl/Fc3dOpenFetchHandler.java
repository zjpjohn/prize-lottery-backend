package com.prize.lottery.infrast.spider.open.impl;

import com.google.common.collect.Maps;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.spider.open.AbsOpenFetchHandler;
import com.prize.lottery.infrast.spider.open.DanMarker;
import com.prize.lottery.mapper.Fc3dIcaiMapper;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.po.fc3d.Fc3dIcaiPo;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.po.master.MasterLotteryPo;
import com.prize.lottery.value.ICaiForecast;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("Fc3dOpenFetchHandler")
public class Fc3dOpenFetchHandler extends AbsOpenFetchHandler {

    private final Map<String, DanMarker> cache = Maps.newConcurrentMap();

    private final Fc3dIcaiMapper    fc3dIcaiMapper;
    private final LotteryInfoMapper lotteryMapper;

    public Fc3dOpenFetchHandler(MasterInfoMapper masterInfoMapper,
                                Fc3dIcaiMapper fc3dIcaiMapper,
                                LotteryInfoMapper lotteryMapper) {
        super(LotteryEnum.FC3D, masterInfoMapper);
        this.fc3dIcaiMapper = fc3dIcaiMapper;
        this.lotteryMapper  = lotteryMapper;
    }

    @Override
    @Transactional
    public void handle(ICaiForecast data) {
        super.handle(data);
        fc3dIcaiMapper.addFc3dIcaiData(toConvert(data));
    }

    @Override
    public List<Pair<String, String>> masters(String period) {
        List<MasterLotteryPo> masters = fc3dIcaiMapper.getAllNoneDataMasters(period);
        return masters.stream().map(e -> Pair.of(e.getMasterId(), e.getSourceId())).collect(Collectors.toList());
    }

    @Override
    public List<Pair<String, String>> incrMasters(String period) {
        List<MasterLotteryPo> masters = fc3dIcaiMapper.getNoneDataMasters(period);
        return masters.stream().map(e -> Pair.of(e.getMasterId(), e.getSourceId())).collect(Collectors.toList());
    }

    @Override
    public DanMarker danMarker(String period) {
        return cache.computeIfAbsent(period, key -> {
            String        lastPeriod = LotteryEnum.FC3D.lastPeriod(period);
            LotteryInfoPo lottery    = lotteryMapper.getLotteryInfo(LotteryEnum.FC3D.getType(), lastPeriod);
            return new DanMarker(lottery);
        });
    }

    private Fc3dIcaiPo toConvert(ICaiForecast data) {
        Fc3dIcaiPo po = new Fc3dIcaiPo();
        po.setPeriod(data.getPeriod());
        po.setMasterId(data.getMasterId());
        po.setGmtCreate(getRandomTime(7200000));
        Arrays.stream(Fc3dChannel.values()).forEach(channel -> channel.toConvert(po, data));
        //双胆标识
        DanMarker danMarker = danMarker(po.getPeriod());
        po.setMark(danMarker.mark(po.getDan2().getData()));
        return po;
    }
}
