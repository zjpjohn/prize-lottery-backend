package com.prize.lottery.application.executor;

import com.alibaba.nacos.shaded.com.google.common.collect.Sets;
import com.google.common.collect.Lists;
import com.prize.lottery.application.cmd.DkRecommendCmd;
import com.prize.lottery.application.cmd.Pl3ComSelectedCmd;
import com.prize.lottery.application.vo.DanKillCalcResult;
import com.prize.lottery.application.vo.Pl3ComRecommendVo;
import com.prize.lottery.application.vo.Pl3DkRecommendVo;
import com.prize.lottery.domain.pl3.model.Pl3PickedAutoCalculator;
import com.prize.lottery.domain.pl3.repository.IPl3RecommendRepository;
import com.prize.lottery.domain.share.model.N3ComRecommendDo;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.enums.Pl3Channel;
import com.prize.lottery.infrast.utils.LottoAnalyzeUtil;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.mapper.Pl3IcaiMapper;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.po.pl3.Pl3LottoCensusPo;
import com.prize.lottery.utils.PeriodCalculator;
import com.prize.lottery.value.ComRecommend;
import com.prize.lottery.vo.ICaiRankedDataVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class Pl3ComRecommendAnaExe {

    private final Pl3IcaiMapper           pl3IcaiMapper;
    private final LotteryInfoMapper       lotteryInfoMapper;
    private final IPl3RecommendRepository recommendRepository;

    public DanKillCalcResult calculate(String period, ChartType type) {
        List<String>            channels   = Lists.newArrayList(Pl3Channel.KILL1.getChannel(), Pl3Channel.KILL2.getChannel());
        List<Pl3LottoCensusPo>  censusList = pl3IcaiMapper.getVipOrItemCensusList(period, type.getType(), channels);
        Pl3PickedAutoCalculator calculator = new Pl3PickedAutoCalculator(censusList);
        calculator.calculate();
        DanKillCalcResult result = new DanKillCalcResult();
        result.setKWeight(calculator.getKWeight());
        result.setDanList(calculator.getDanList());
        result.setKillList(calculator.getKillList());
        return result;
    }

    public Pl3DkRecommendVo execute(DkRecommendCmd cmd) {
        String            period        = cmd.getPeriod();
        DanKillCalcResult danKillResult = this.calculate(period, ChartType.ITEM_CHART);
        if (CollectionUtils.isEmpty(danKillResult.getDanList())) {
            return Pl3DkRecommendVo.empty(period);
        }
        String                 last  = PeriodCalculator.pl3Period(period, 1);
        List<ICaiRankedDataVo> datas = pl3IcaiMapper.getPl3ComparedDatas(Pl3Channel.COM7.value(), period, last, 20);
        if (CollectionUtils.isEmpty(datas)) {
            return Pl3DkRecommendVo.empty(period);
        }
        Set<String> killList = Sets.newHashSet(danKillResult.getKillList());
        if (!CollectionUtils.isEmpty(cmd.getKills())) {
            killList.addAll(cmd.getKills());
        }
        if (!CollectionUtils.isEmpty(cmd.getExcludes())) {
            cmd.getExcludes().forEach(killList::remove);
        }
        N3ComRecommendDo recommendDo = this.calcRecommend(period, datas, danKillResult.getDanList(), Lists.newArrayList(killList), cmd.getKuas());
        return Pl3DkRecommendVo.fromDo(recommendDo, danKillResult.getKWeight());
    }

    /**
     * 排列三组合号码推荐
     */
    public Pl3ComRecommendVo execute(Pl3ComSelectedCmd cmd) {
        String                 last  = LotteryEnum.PL3.lastPeriod(cmd.getPeriod());
        List<ICaiRankedDataVo> datas = pl3IcaiMapper.getPl3ComparedDatas(Pl3Channel.COM7.value(), cmd.getPeriod(), last, 20);
        if (CollectionUtils.isEmpty(datas)) {
            return Pl3ComRecommendVo.empty(cmd.getPeriod());
        }
        List<String>     dans        = cmd.getDans(), kills = cmd.getKills();
        List<Integer>    kuas        = cmd.getKuas();
        N3ComRecommendDo recommendDo = this.calcRecommend(cmd.getPeriod(), datas, dans, kills, kuas);
        if (cmd.getSave()) {
            recommendRepository.save(recommendDo);
        }
        return Pl3ComRecommendVo.fromDo(recommendDo);
    }

    private N3ComRecommendDo calcRecommend(String period,
                                           List<ICaiRankedDataVo> datas,
                                           List<String> dans,
                                           List<String> kills,
                                           List<Integer> kuas) {
        List<List<String>> lists = datas.stream()
                                        .map(e -> Lists.newArrayList(e.getData().split()))
                                        .filter(e -> LottoAnalyzeUtil.isContainDans(e, dans))
                                        .peek(e -> e.removeAll(kills))
                                        .collect(Collectors.toList());
        CompletableFuture<Set<String>> zu6Future = CompletableFuture.supplyAsync(() -> LottoAnalyzeUtil.n3ComAnalyze(lists, dans, kuas, 3, 3, 11));
        CompletableFuture<Set<String>> zu3Future = CompletableFuture.supplyAsync(() -> LottoAnalyzeUtil.n3ComAnalyze(lists, dans, kuas, 2, 7, 12));
        CompletableFuture<N3ComRecommendDo> combine = zu6Future.thenCombine(zu3Future, (zu6, zu3) -> {
            ComRecommend     zu6Rec      = ComRecommend.zu6(Lists.newArrayList(zu6));
            ComRecommend     zu3Rec      = ComRecommend.zu3(Lists.newArrayList(zu3));
            N3ComRecommendDo recommendDo = new N3ComRecommendDo(period, zu6Rec, zu3Rec);
            recommendDo.addDanWarning(dans);
            recommendDo.addKillWarning(kills);
            LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(LotteryEnum.PL3.getType(), period);
            if (lottery != null) {
                recommendDo.calcHit(lottery.redBalls());
            }
            return recommendDo;
        });
        return combine.join();
    }

    public Pair<List<String>, List<String>> calcRecommend(String period,
                                                          List<String> danList,
                                                          List<Integer> kuaList,
                                                          List<Integer> sumList) {
        String                 last  = LotteryEnum.PL3.lastPeriod(period);
        List<ICaiRankedDataVo> datas = pl3IcaiMapper.getPl3ComparedDatas(Pl3Channel.COM7.value(), period, last, 20);
        if (CollectionUtils.isEmpty(datas)) {
            return Pair.of(Lists.newArrayList(), Lists.newArrayList());
        }
        List<List<String>> lists = datas.stream()
                                        .map(e -> Lists.newArrayList(e.getData().split()))
                                        .filter(e -> LottoAnalyzeUtil.filterSum(e, sumList))
                                        .filter(e -> LottoAnalyzeUtil.isContainDans(e, danList))
                                        .collect(Collectors.toList());
        CompletableFuture<Set<String>>                      zu6Future = CompletableFuture.supplyAsync(() -> LottoAnalyzeUtil.n3ComAnalyze(lists, danList, kuaList, 3, 3, 11));
        CompletableFuture<Set<String>>                      zu3Future = CompletableFuture.supplyAsync(() -> LottoAnalyzeUtil.n3ComAnalyze(lists, danList, kuaList, 2, 7, 12));
        CompletableFuture<Pair<List<String>, List<String>>> combine   = zu6Future.thenCombine(zu3Future, (zu6, zu3) -> Pair.of(Lists.newArrayList(zu6), Lists.newArrayList(zu3)));
        return combine.join();
    }
}
