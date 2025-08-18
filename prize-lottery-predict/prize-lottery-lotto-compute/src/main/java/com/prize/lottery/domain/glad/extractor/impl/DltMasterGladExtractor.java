package com.prize.lottery.domain.glad.extractor.impl;

import com.google.common.collect.Lists;
import com.prize.lottery.domain.glad.domain.MasterGladDo;
import com.prize.lottery.domain.glad.extractor.GladExtractor;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.DltMasterMapper;
import com.prize.lottery.vo.MasterGladRateVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class DltMasterGladExtractor implements GladExtractor {

    public static final String R20_GLAD_CONTENT = "红球20码全部命中";
    public static final String RK3_GLAD_CONTENT = "红球杀码高达%d%%";

    private final LotteryEnum     type;
    private final DltMasterMapper dltMasterMapper;

    public DltMasterGladExtractor(DltMasterMapper dltMasterMapper) {
        this.type            = LotteryEnum.DLT;
        this.dltMasterMapper = dltMasterMapper;
    }

    @Override
    public LotteryEnum bizIndex() {
        return this.type;
    }

    @Override
    public List<MasterGladDo> extract() {
        String period = dltMasterMapper.latestDltRatePeriod();
        if (StringUtils.isBlank(period)) {
            return Collections.emptyList();
        }
        List<MasterGladDo> glads     = Lists.newArrayList();
        MasterGladRateVo   r20Master = dltMasterMapper.getDltR20BestMaster(period);
        if (r20Master != null) {
            MasterGladDo glad = MasterGladDo.hitLottery(LotteryEnum.DLT, r20Master.getMasterId(), period, R20_GLAD_CONTENT);
            glads.add(glad);
        }
        List<MasterGladRateVo> rk3Masters = dltMasterMapper.getDltRk3BestMasters(period);
        rk3Masters.stream()
                  .filter(master -> r20Master == null || !master.getMasterId().equals(r20Master.getMasterId()))
                  .findFirst()
                  .ifPresent(master -> {
                      int          rate    = Double.valueOf(master.getRate() * 100).intValue();
                      String       content = String.format(RK3_GLAD_CONTENT, rate);
                      MasterGladDo glad    = MasterGladDo.highRate(LotteryEnum.DLT, master.getMasterId(), period, content);
                      glads.add(glad);
                  });
        return glads;
    }
}
