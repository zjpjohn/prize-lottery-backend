package com.prize.lottery.application.service.impl;

import com.cloud.arch.aggregate.Aggregate;
import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.utils.CollectionUtils;
import com.prize.lottery.application.cmd.Num3ComWarnCmd;
import com.prize.lottery.application.executor.Fc3dComRecommendAnaExe;
import com.prize.lottery.application.executor.Pl3ComRecommendAnaExe;
import com.prize.lottery.application.service.INum3ComWarnService;
import com.prize.lottery.domain.share.model.Num3ComWarnDo;
import com.prize.lottery.domain.share.repository.INum3ComWarnRepository;
import com.prize.lottery.domain.share.valobj.ComWarnValue;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.utils.ICaiConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class Num3ComWarnService implements INum3ComWarnService {

    private final LotteryInfoMapper      mapper;
    private final INum3ComWarnRepository repository;
    private final Fc3dComRecommendAnaExe fc3dRecommendAnaExe;
    private final Pl3ComRecommendAnaExe  pl3RecommendAnaExe;

    @Override
    @Transactional
    public void createComWarn(Num3ComWarnCmd cmd) {
        ComWarnValue warnValue = cmd.checkAndBuild();
        String       period    = cmd.getPeriod();
        LotteryEnum  type      = cmd.getType();
        Aggregate<Long, Num3ComWarnDo> aggregate = repository.ofUk(type, period)
                                                             .map(agg -> agg.peek(root -> root.modify(warnValue)))
                                                             .orElseGet(() -> {
                                                                 Num3ComWarnDo comWarnDo = new Num3ComWarnDo(period, type, warnValue);
                                                                 return AggregateFactory.create(comWarnDo);
                                                             });

        ///组选7码进行数据过滤进一步缩水
        Pair<List<String>, List<String>> pair = this.comRecommends(cmd);
        aggregate.peek(root -> root.filter(pair.getKey(), pair.getValue()));

        ///开奖数据进行开奖计算
        LotteryInfoPo lottery = mapper.getLotteryInfo(type.getType(), period);
        if (lottery != null) {
            Map<String, Integer> judgeLottery = ICaiConstants.judgeLottery(lottery.redBalls());
            if (CollectionUtils.isNotEmpty(judgeLottery)) {
                aggregate.peek(root -> root.calcHit(judgeLottery));
            }
        }
        repository.save(aggregate);
    }

    private Pair<List<String>, List<String>> comRecommends(Num3ComWarnCmd cmd) {
        if (cmd.getType() == LotteryEnum.FC3D) {
            return fc3dRecommendAnaExe.calcRecommend(cmd.getPeriod(), cmd.getDanList(), cmd.getKuaList(), cmd.getSumList());
        }
        return pl3RecommendAnaExe.calcRecommend(cmd.getPeriod(), cmd.getDanList(), cmd.getKuaList(), cmd.getSumList());
    }

    @Override
    @Transactional
    public void calcComWarnHit(LotteryEnum type, String period) {
        repository.ofUk(type, period).ifPresent(aggregate -> {
            LotteryInfoPo lottery = mapper.getLotteryInfo(type.getType(), period);
            if (lottery != null) {
                calcWarnHit(aggregate, lottery);
            }
        });
    }

    @Override
    @Transactional
    public void calcWarnHit(Long id) {
        repository.ofId(id).ifPresent(aggregate -> {
            Num3ComWarnDo warn    = aggregate.getRoot();
            LotteryInfoPo lottery = mapper.getLotteryInfo(warn.getType().getType(), warn.getPeriod());
            if (lottery != null) {
                calcWarnHit(aggregate, lottery);
            }
        });
    }

    private void calcWarnHit(Aggregate<Long, Num3ComWarnDo> aggregate, LotteryInfoPo lottery) {
        List<String> balls = lottery.redBalls();
        if (CollectionUtils.isEmpty(balls)) {
            return;
        }
        Map<String, Integer> judgeLottery = ICaiConstants.judgeLottery(balls);
        aggregate.peek(root -> root.calcHit(judgeLottery));
        repository.save(aggregate);
    }
}
