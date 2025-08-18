package com.prize.lottery.domain.glad.extractor.impl;

import com.google.common.collect.Lists;
import com.prize.lottery.domain.glad.domain.MasterGladDo;
import com.prize.lottery.domain.glad.extractor.GladExtractor;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.Fc3dMasterMapper;
import com.prize.lottery.vo.MasterGladRateVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class Fc3dMasterGladExtractor implements GladExtractor {

    public static final String D3_HIT_CONTENT = "三胆预测命中";
    public static final String C7_HIT_CONTENT = "七码预测高达%d%%";

    private final LotteryEnum      lottery;
    private final Fc3dMasterMapper fc3dMasterMapper;

    public Fc3dMasterGladExtractor(Fc3dMasterMapper fc3dMasterMapper) {
        this.lottery          = LotteryEnum.FC3D;
        this.fc3dMasterMapper = fc3dMasterMapper;
    }

    @Override
    public LotteryEnum bizIndex() {
        return this.lottery;
    }

    @Override
    public List<MasterGladDo> extract() {
        String period = fc3dMasterMapper.latestFc3dRatePeriod();
        if (StringUtils.isBlank(period)) {
            return Collections.emptyList();
        }
        List<MasterGladDo> glads    = Lists.newArrayList();
        MasterGladRateVo   d3Master = fc3dMasterMapper.getFc3dD3HitBestMaster(period);
        MasterGladDo hitLottery =
                MasterGladDo.hitLottery(LotteryEnum.FC3D, d3Master.getMasterId(), period, D3_HIT_CONTENT);
        glads.add(hitLottery);

        List<MasterGladRateVo> c7Masters = fc3dMasterMapper.getFc3dC7BestMasters(period);
        c7Masters.stream()
                 .filter(master -> !master.getMasterId().equals(d3Master.getMasterId()))
                 .findFirst()
                 .ifPresent(master -> {
                     int    rate    = Double.valueOf(master.getRate() * 100).intValue();
                     String content = String.format(C7_HIT_CONTENT, rate);
                     MasterGladDo masterGlad =
                             MasterGladDo.highRate(LotteryEnum.FC3D, master.getMasterId(), period, content);
                     glads.add(masterGlad);
                 });
        return glads;
    }

}
