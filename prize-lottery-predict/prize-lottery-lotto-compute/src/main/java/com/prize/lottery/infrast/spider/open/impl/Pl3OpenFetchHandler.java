package com.prize.lottery.infrast.spider.open.impl;

import com.google.common.collect.Maps;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.Pl3Channel;
import com.prize.lottery.infrast.spider.open.AbsOpenFetchHandler;
import com.prize.lottery.infrast.spider.open.DanMarker;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.mapper.Pl3IcaiMapper;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.po.master.MasterLotteryPo;
import com.prize.lottery.po.pl3.Pl3IcaiPo;
import com.prize.lottery.value.ICaiForecast;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("Pl3OpenFetchHandler")
public class Pl3OpenFetchHandler extends AbsOpenFetchHandler {

    private final Map<String, DanMarker> cache = Maps.newConcurrentMap();

    private final Pl3IcaiMapper     pl3IcaiMapper;
    private final LotteryInfoMapper lotteryMapper;

    public Pl3OpenFetchHandler(MasterInfoMapper masterInfoMapper,
                               Pl3IcaiMapper pl3IcaiMapper,
                               LotteryInfoMapper lotteryMapper) {
        super(LotteryEnum.PL3, masterInfoMapper);
        this.pl3IcaiMapper = pl3IcaiMapper;
        this.lotteryMapper = lotteryMapper;
    }

    @Override
    @Transactional
    public void handle(ICaiForecast data) {
        super.handle(data);
        pl3IcaiMapper.addPl3IcaiData(toConvert(data));
    }

    @Override
    public List<Pair<String, String>> masters(String period) {
        List<MasterLotteryPo> masters = pl3IcaiMapper.getNoneDataMasters(period);
        return masters.stream().map(e -> Pair.of(e.getMasterId(), e.getSourceId())).collect(Collectors.toList());
    }

    @Override
    public List<Pair<String, String>> incrMasters(String period) {
        List<MasterLotteryPo> masters = pl3IcaiMapper.getAllNoneDataMasters(period);
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

    private Pl3IcaiPo toConvert(ICaiForecast data) {
        Pl3IcaiPo po = new Pl3IcaiPo();
        po.setPeriod(data.getPeriod());
        po.setMasterId(data.getMasterId());
        po.setGmtCreate(getRandomTime(7200000));
        Arrays.stream(Pl3Channel.values()).forEach(channel -> channel.toConvert(po, data));
        DanMarker danMarker = danMarker(po.getPeriod());
        po.setMark(danMarker.mark(po.getDan2().getData()));
        return po;
    }
}
