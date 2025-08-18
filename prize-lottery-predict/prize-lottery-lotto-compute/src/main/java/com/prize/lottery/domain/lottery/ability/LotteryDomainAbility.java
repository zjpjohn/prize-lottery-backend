package com.prize.lottery.domain.lottery.ability;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.lottery.assembler.LotteryInfoAssembler;
import com.prize.lottery.domain.lottery.facade.LotteryInfoFacade;
import com.prize.lottery.domain.lottery.facade.LotteryTrialFacade;
import com.prize.lottery.domain.lottery.model.LotteryInfoDo;
import com.prize.lottery.domain.lottery.model.LotteryTrialDo;
import com.prize.lottery.domain.lottery.value.LevelValue;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryAwardPo;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.po.lottery.LotteryLevelPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LotteryDomainAbility {

    private final LotteryInfoAssembler lotteryInfoAssembler;
    private final LotteryInfoFacade    lotteryInfoFacade;
    private final LotteryTrialFacade   lotteryTrialFacade;
    private final LotteryInfoMapper    lotteryInfoMapper;

    public void initCalcNum3Com(LotteryEnum type, Integer limit) {
        List<LotteryInfoPo> lotteries = lotteryInfoMapper.getLimitLotteryList(type, limit);
        lotteries.forEach(LotteryInfoPo::calcNum3Com);
        lotteryInfoMapper.editN3Lotteries(lotteries);
    }

    public void initN3LastSame(LotteryEnum type, Integer limit) {
        List<LotteryInfoPo> lotteries = lotteryInfoMapper.getLimitLotteryList(type, limit);
        List<LotteryInfoPo> result    = Lists.newArrayList();
        for (LotteryInfoPo lottery : lotteries) {
            LotteryInfoPo sameLottery = lotteryInfoMapper.getN3LastSameLottery(type, lottery.getCom(), lottery.getPeriod());
            if (sameLottery != null) {
                lottery.setLast(sameLottery.getPeriod());
                result.add(lottery);
            }
        }
        if (!CollectionUtils.isEmpty(result)) {
            lotteryInfoMapper.editN3Lotteries(result);
        }
    }

    public void calcNum3Same(LotteryEnum type, String period) {
        if (type == LotteryEnum.FC3D || type == LotteryEnum.PL3) {
            final LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(type.getType(), period);
            if (lottery != null) {
                String        num3Com    = lottery.calcNum3Com();
                LotteryInfoPo last       = lotteryInfoMapper.getN3LastSameLottery(type, num3Com, lottery.getPeriod());
                LotteryInfoPo newLottery = new LotteryInfoPo();
                newLottery.setId(lottery.getId());
                newLottery.setCom(num3Com);
                newLottery.setLast(last.getPeriod());
                lotteryInfoMapper.editN3Lotteries(Lists.newArrayList(newLottery));
            }
        }
    }

    @Transactional
    public void fetchLotteryDetail(LotteryEnum type) {
        LotteryInfoDo lottery = lotteryInfoFacade.getLatestLotteryInfo(type);
        if (lottery != null) {
            //开奖信息
            LotteryInfoPo lotteryPo = lotteryInfoAssembler.toLottery(lottery);
            lotteryInfoMapper.addLotteryInfos(Lists.newArrayList(lotteryPo));
            //抓取开奖奖金信息
            lotteryAwards(lottery);
        }
    }

    @Transactional
    public String fetchLatestLottery(LotteryEnum type) {
        LotteryInfoDo lottery = lotteryInfoFacade.getLatestLotteryInfo(type);
        if (lottery == null || lotteryInfoMapper.existLottery(type.getType(), lottery.getPeriod()) == 1) {
            //开奖号不存在或奖号已经入库，返回最新期号为空
            return null;
        }
        //开奖信息
        LotteryInfoPo lotteryPo = lotteryInfoAssembler.toLottery(lottery);
        lotteryInfoMapper.addLotteryInfos(Lists.newArrayList(lotteryPo));
        //抓取开奖奖金信息
        lotteryAwards(lottery);
        //返回抓取的最新开奖期号
        return lottery.getPeriod();
    }

    private void lotteryAwards(LotteryInfoDo lottery) {
        //中奖奖金
        LotteryAwardPo award = lotteryInfoAssembler.toAward(lottery);
        lotteryInfoMapper.addLotteryAwards(Lists.newArrayList(award));

        //中奖等级信息
        List<LotteryLevelPo> levels = lottery.getLevels()
                                             .stream()
                                             .map(v -> lotteryInfoAssembler.toLevel(lottery, v))
                                             .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(levels)) {
            lotteryInfoMapper.addLotteryLevels(levels);
        }
    }

    @Transactional
    public String fetchLotteries(LotteryEnum lottery, Integer size) {
        List<LotteryInfoDo> lotteryDos = lotteryInfoFacade.getLotteryList(lottery, size);
        Assert.state(!CollectionUtils.isEmpty(lotteryDos), ResponseHandler.NONE_FETCHED_RESULT);
        this.saveLotteries(lotteryDos);
        return lotteryDos.get(0).getPeriod();
    }

    @Transactional
    public String fetchLotteries(LotteryEnum type, String start, String end) {
        List<LotteryInfoDo> lotteryDos = lotteryInfoFacade.getLotteryList(type, start, end);
        Assert.state(!CollectionUtils.isEmpty(lotteryDos), ResponseHandler.NONE_FETCHED_RESULT);
        this.saveLotteries(lotteryDos);
        return lotteryDos.get(0).getPeriod();
    }

    private void saveLotteries(List<LotteryInfoDo> lotteryDos) {
        //开奖数据
        List<LotteryInfoPo> lotteries = lotteryInfoAssembler.toLotteries(lotteryDos);
        lotteryInfoMapper.addLotteryInfos(lotteries);

        //中奖汇总信息
        List<LotteryAwardPo> awards = lotteryInfoAssembler.toAwards(lotteryDos);
        lotteryInfoMapper.addLotteryAwards(awards);

        //中奖等级数据
        List<LotteryLevelPo> levelPos = lotteryDos.stream()
                                                  .filter(v -> !CollectionUtils.isEmpty(v.getLevels()))
                                                  .flatMap(v -> {
                                                      List<LevelValue> levels = v.getLevels();
                                                      return levels.stream()
                                                                   .map(level -> lotteryInfoAssembler.toLevel(v, level));
                                                  })
                                                  .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(levelPos)) {
            lotteryInfoMapper.addLotteryLevels(levelPos);
        }
    }

    /**
     * 批量抓取试机号
     *
     * @param lottery 彩票类型
     * @param size    抓取数量
     */
    public void fetchLotteryTrials(LotteryEnum lottery, Integer size) {
        List<LotteryTrialDo> trialDos = lotteryTrialFacade.getLotteryTrialList(lottery, size);
        Assert.state(!CollectionUtils.isEmpty(trialDos), ResponseHandler.NONE_FETCHED_RESULT);
        List<LotteryInfoPo> trails = lotteryInfoAssembler.toTrials(trialDos);
        lotteryInfoMapper.addLotteryTrials(trails);
    }

    /**
     * 抓取指定期号的试机号
     *
     * @param lottery 彩票类型
     * @param period  开奖期号
     */
    public void fetchLotteryTrial(LotteryEnum lottery, String period) {
        LotteryInfoPo lotteryInfo = lotteryInfoMapper.getLotteryInfo(lottery.getType(), period);
        if (lotteryInfo == null || StringUtils.isBlank(lotteryInfo.getShi())) {
            lotteryTrialFacade.getLotteryTrial(lottery, period).ifPresent(trial -> {
                LotteryInfoPo trialPo = lotteryInfoAssembler.toTrial(trial);
                lotteryInfoMapper.addLotteryTrial(trialPo);
            });
        }
    }

}
