package com.prize.lottery.domain.glad.extractor.impl;

import com.google.common.collect.Lists;
import com.prize.lottery.domain.glad.domain.MasterGladDo;
import com.prize.lottery.domain.glad.extractor.GladExtractor;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.Pl3MasterMapper;
import com.prize.lottery.vo.MasterGladRateVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class Pl3MasterGladExtractor implements GladExtractor {

    public static final String D3_HIT_CONTENT = "三胆预测命中";
    public static final String C7_HIT_CONTENT = "七码预测高达%d%%";

    private final LotteryEnum     type;
    private final Pl3MasterMapper pl3MasterMapper;

    public Pl3MasterGladExtractor(Pl3MasterMapper pl3MasterMapper) {
        this.type            = LotteryEnum.PL3;
        this.pl3MasterMapper = pl3MasterMapper;
    }

    @Override
    public LotteryEnum bizIndex() {
        return this.type;
    }

    @Override
    public List<MasterGladDo> extract() {
        String period = pl3MasterMapper.latestPl3RatePeriod();
        if (StringUtils.isBlank(period)) {
            return Collections.emptyList();
        }
        List<MasterGladDo> glads    = Lists.newArrayList();
        MasterGladRateVo   d3Master = pl3MasterMapper.getPl3D3HitBestMaster(period);
        MasterGladDo       d3Glad   = MasterGladDo.hitLottery(LotteryEnum.PL3, d3Master.getMasterId(), period, D3_HIT_CONTENT);
        glads.add(d3Glad);

        List<MasterGladRateVo> c7Masters = pl3MasterMapper.getPl3C7BestMasters(period);
        c7Masters.stream()
                 .filter(master -> master.getMasterId().equals(d3Master.getMasterId()))
                 .findFirst()
                 .ifPresent(master -> {
                     int          rate    = Double.valueOf(master.getRate() * 100).intValue();
                     String       content = String.format(C7_HIT_CONTENT, rate);
                     MasterGladDo glad    = MasterGladDo.highRate(LotteryEnum.PL3, master.getMasterId(), period, content);
                     glads.add(glad);
                 });
        return glads;
    }
}
