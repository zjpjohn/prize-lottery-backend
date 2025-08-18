package com.prize.lottery.infrast.spider.open.impl;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.QlcChannel;
import com.prize.lottery.infrast.spider.open.AbsOpenFetchHandler;
import com.prize.lottery.mapper.MasterInfoMapper;
import com.prize.lottery.mapper.QlcIcaiMapper;
import com.prize.lottery.po.master.MasterLotteryPo;
import com.prize.lottery.po.qlc.QlcIcaiPo;
import com.prize.lottery.value.ICaiForecast;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component("QlcOpenFetchHandler")
public class QlcOpenFetchHandler extends AbsOpenFetchHandler {

    private final QlcIcaiMapper qlcIcaiMapper;

    public QlcOpenFetchHandler(MasterInfoMapper masterInfoMapper, QlcIcaiMapper qlcIcaiMapper) {
        super(LotteryEnum.QLC, masterInfoMapper);
        this.qlcIcaiMapper = qlcIcaiMapper;
    }

    @Override
    @Transactional
    public void handle(ICaiForecast data) {
        super.handle(data);
        qlcIcaiMapper.addQlcICai(toConvert(data));
    }

    @Override
    public List<Pair<String, String>> masters(String period) {
        List<MasterLotteryPo> masters = qlcIcaiMapper.getAllNoneDataMasters(period);
        return masters.stream().map(e -> Pair.of(e.getMasterId(), e.getSourceId())).collect(Collectors.toList());
    }

    @Override
    public List<Pair<String, String>> incrMasters(String period) {
        List<MasterLotteryPo> masters = qlcIcaiMapper.getNoneDataMasters(period);
        return masters.stream().map(e -> Pair.of(e.getMasterId(), e.getSourceId())).collect(Collectors.toList());
    }


    private QlcIcaiPo toConvert(ICaiForecast data) {
        QlcIcaiPo po = new QlcIcaiPo();
        po.setMasterId(data.getMasterId());
        po.setPeriod(data.getPeriod());
        po.setGmtCreate(getRandomTime(7200000));
        Arrays.stream(QlcChannel.values()).forEach(channel -> channel.toConvert(po, data));
        return po;
    }

}
