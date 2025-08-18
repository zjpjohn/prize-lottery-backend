package com.prize.lottery.application.service.impl;

import com.cloud.arch.web.utils.Assert;
import com.prize.lottery.application.cmd.AroundBatchCmd;
import com.prize.lottery.application.cmd.AroundSingleCmd;
import com.prize.lottery.application.cmd.HoneySingleCmd;
import com.prize.lottery.application.cmd.TrialCreateCmd;
import com.prize.lottery.application.service.ILotteryInfoService;
import com.prize.lottery.application.service.IProxyCalcHandler;
import com.prize.lottery.domain.lottery.ability.AroundDomainAbility;
import com.prize.lottery.domain.lottery.ability.HoneyDomainAbility;
import com.prize.lottery.domain.lottery.ability.LotteryDomainAbility;
import com.prize.lottery.domain.lottery.model.LotteryFairTrialDo;
import com.prize.lottery.domain.lottery.repository.IAroundRepository;
import com.prize.lottery.domain.lottery.repository.IFairTrialRepository;
import com.prize.lottery.domain.lottery.repository.IHoneyRepository;
import com.prize.lottery.domain.omit.ability.*;
import com.prize.lottery.domain.omit.ability.executor.*;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.infrast.spider.around.Fc3dAroundSpider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LotteryInfoService implements ILotteryInfoService {

    private final KuaExecutor          kuaExecutor;
    private final SumExecutor          sumExecutor;
    private final ItemExecutor         itemExecutor;
    private final TrendExecutor        trendExecutor;
    private final MatchExecutor        matchExecutor;
    private final LotteryDomainAbility lotteryDomainAbility;
    private final LotteryOmitAbility   lotteryOmitAbility;
    private final LotteryCodeAbility   lotteryCodeAbility;
    private final LotteryDanAbility    lotteryDanAbility;
    private final LotteryOttAbility    lotteryOttAbility;
    private final AroundDomainAbility  aroundDomainAbility;
    private final HoneyDomainAbility   honeyDomainAbility;
    private final IAroundRepository    aroundRepository;
    private final IHoneyRepository     honeyRepository;
    private final IProxyCalcHandler    proxyCalcHandler;
    private final LottoN3PianAbility   lottoPianAbility;
    private final IFairTrialRepository trialRepository;
    private final Fc3dAroundSpider     fc3dAroundSpider;

    @Override
    public String fetchLatestLottery(String type) {
        return lotteryDomainAbility.fetchLatestLottery(LotteryEnum.findOf(type));
    }

    @Override
    public String fetchLotteries(String type, Integer size) {
        return lotteryDomainAbility.fetchLotteries(LotteryEnum.findOf(type), size);
    }

    @Override
    public String fetchLotteries(String type, String start, String end) {
        return lotteryDomainAbility.fetchLotteries(LotteryEnum.findOf(type), start, end);
    }

    @Override
    public void fetchLotteryTrials(String type, Integer size) {
        lotteryDomainAbility.fetchLotteryTrials(LotteryEnum.findOf(type), size);
    }

    @Override
    public void fetchLotteryTrial(String type, String period) {
        lotteryDomainAbility.fetchLotteryTrial(LotteryEnum.findOf(type), period);
    }

    @Override
    @Transactional
    public void loadInitialOmit() {
        lotteryOmitAbility.loadOmits();
    }

    @Override
    public void calcLotteryOmit(LotteryEnum type) {
        lotteryOmitAbility.initCalcOmit(type);
    }

    @Override
    public void loadKuaOmit(LotteryEnum type) {
        Assert.state(type == LotteryEnum.PL3
                             || type == LotteryEnum.FC3D
                             || type == LotteryEnum.PL5, ResponseHandler.LOTTO_TYPE_ERROR);
        kuaExecutor.load(type);
    }

    @Override
    public void calcKuaOmit(LotteryEnum type) {
        Assert.state(type == LotteryEnum.PL3
                             || type == LotteryEnum.FC3D
                             || type == LotteryEnum.PL5, ResponseHandler.LOTTO_TYPE_ERROR);
        kuaExecutor.initialize(type);
    }

    @Override
    public void loadSumOmit(LotteryEnum type) {
        Assert.state(type == LotteryEnum.PL3
                             || type == LotteryEnum.FC3D
                             || type == LotteryEnum.PL5, ResponseHandler.LOTTO_TYPE_ERROR);
        sumExecutor.load(type);
    }

    @Override
    public void calcSumOmit(LotteryEnum type) {
        Assert.state(type == LotteryEnum.PL3
                             || type == LotteryEnum.FC3D
                             || type == LotteryEnum.PL5, ResponseHandler.LOTTO_TYPE_ERROR);
        sumExecutor.initialize(type);
    }

    @Override
    public void loadTrendOmit(LotteryEnum type) {
        Assert.state(type == LotteryEnum.PL3 || type == LotteryEnum.FC3D, ResponseHandler.LOTTO_TYPE_ERROR);
        trendExecutor.load(type);
    }

    @Override
    public void calcTrendOmit(LotteryEnum type) {
        Assert.state(type == LotteryEnum.PL3 || type == LotteryEnum.FC3D, ResponseHandler.LOTTO_TYPE_ERROR);
        trendExecutor.initialize(type);
    }

    @Override
    public void loadMatchOmit(LotteryEnum type) {
        Assert.state(type == LotteryEnum.PL3 || type == LotteryEnum.FC3D, ResponseHandler.LOTTO_TYPE_ERROR);
        matchExecutor.load(type);
    }

    @Override
    public void calcMatchOmit(LotteryEnum type) {
        Assert.state(type == LotteryEnum.PL3 || type == LotteryEnum.FC3D, ResponseHandler.LOTTO_TYPE_ERROR);
        matchExecutor.initialize(type);
    }

    @Override
    public void loadItemOmit(LotteryEnum type) {
        Assert.state(type == LotteryEnum.PL3
                             || type == LotteryEnum.FC3D
                             || type == LotteryEnum.PL5, ResponseHandler.LOTTO_TYPE_ERROR);
        itemExecutor.load(type);
    }

    @Override
    public void calcItemOmit(LotteryEnum type) {
        Assert.state(type == LotteryEnum.PL3
                             || type == LotteryEnum.FC3D
                             || type == LotteryEnum.PL5, ResponseHandler.LOTTO_TYPE_ERROR);
        itemExecutor.initialize(type);
    }

    @Override
    public void codeInitialize(LotteryEnum type) {
        Assert.state(type == LotteryEnum.PL3 || type == LotteryEnum.FC3D, ResponseHandler.LOTTO_TYPE_ERROR);
        lotteryCodeAbility.initialize(type, 100);
    }

    @Override
    public void initLottoDan(LotteryEnum type) {
        Assert.state(type == LotteryEnum.PL3 || type == LotteryEnum.FC3D, ResponseHandler.LOTTO_TYPE_ERROR);
        lotteryDanAbility.initAndLoad(type);
    }

    @Override
    public void initLottoOtt(LotteryEnum type) {
        Assert.state(type == LotteryEnum.PL3 || type == LotteryEnum.FC3D, ResponseHandler.LOTTO_TYPE_ERROR);
        lotteryOttAbility.initialize(type, 100);
    }

    @Override
    public void addAroundSingle(AroundSingleCmd cmd) {
        LotteryEnum lotto = cmd.getLotto();
        Assert.state(lotto == LotteryEnum.PL3 || lotto == LotteryEnum.FC3D, ResponseHandler.LOTTO_TYPE_ERROR);
        aroundDomainAbility.addAround(cmd);
    }

    @Override
    public void addAroundBatch(AroundBatchCmd cmd) {
        LotteryEnum lotto = cmd.getLotto();
        Assert.state(lotto == LotteryEnum.PL3 || lotto == LotteryEnum.FC3D, ResponseHandler.LOTTO_TYPE_ERROR);
        aroundDomainAbility.addAroundList(cmd);
    }

    @Override
    public void calcAroundResult(LotteryEnum type, String period) {
        aroundDomainAbility.calcAroundResult(type, period);
    }

    @Override
    public void removeAround(Long id) {
        aroundRepository.remove(id);
    }

    @Override
    public void fetchAround(Integer size) {
        if (size <= 0) {
            fc3dAroundSpider.fetchAround(null);
            return;
        }
        fc3dAroundSpider.fetchAround(size, size * 30);
    }

    @Override
    public void addHoneySingle(LotteryEnum type, HoneySingleCmd cmd) {
        Assert.state(type == LotteryEnum.PL3 || type == LotteryEnum.FC3D, ResponseHandler.LOTTO_TYPE_ERROR);
        honeyDomainAbility.addHoney(type, cmd);
    }

    @Override
    public void addHoneyBatch(LotteryEnum type, List<HoneySingleCmd> cmdList) {
        Assert.state(type == LotteryEnum.PL3 || type == LotteryEnum.FC3D, ResponseHandler.LOTTO_TYPE_ERROR);
        honeyDomainAbility.addHoneyList(type, cmdList);
    }

    @Override
    public void removeHoney(Long id) {
        honeyRepository.remove(id);
    }

    @Override
    public void calcNum3Index(LotteryEnum type, String period) {
        proxyCalcHandler.calcItemIndex(type, period);
    }

    @Override
    public void initPianOmits(LotteryEnum type) {
        Assert.state(type == LotteryEnum.FC3D || type == LotteryEnum.PL3, ResponseHandler.LOTTO_TYPE_ERROR);
        lottoPianAbility.initialize(type, 300);
    }

    @Override
    public void calcPianOmit(LotteryEnum type, String period) {
        lottoPianAbility.execute(type, period);
    }

    @Override
    public void createFaireTrial(TrialCreateCmd command) {
        List<LotteryFairTrialDo> trialDos = LotteryFairTrialDo.create(command.getType(), command.convert());
        trialRepository.save(trialDos);
    }

    @Override
    public void initCalcNum3Com(LotteryEnum type, Integer limit) {
        lotteryDomainAbility.initCalcNum3Com(type, limit);
    }

    @Override
    public void initCalcNum3Same(LotteryEnum type, Integer limit) {
        lotteryDomainAbility.initN3LastSame(type, limit);
    }

}
