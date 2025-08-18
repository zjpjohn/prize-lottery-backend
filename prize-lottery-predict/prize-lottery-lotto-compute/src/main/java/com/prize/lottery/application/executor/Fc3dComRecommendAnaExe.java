package com.prize.lottery.application.executor;

import com.alibaba.nacos.shaded.com.google.common.collect.Sets;
import com.google.common.collect.Lists;
import com.prize.lottery.application.cmd.DkRecommendCmd;
import com.prize.lottery.application.cmd.Fc3dComSelectCmd;
import com.prize.lottery.application.vo.DanKillCalcResult;
import com.prize.lottery.application.vo.Fc3dComRecommendVo;
import com.prize.lottery.application.vo.Fc3dDkRecommendVo;
import com.prize.lottery.domain.fc3d.model.Fc3dPickedAutoCalculator;
import com.prize.lottery.domain.fc3d.repository.IFc3dRecommendRepository;
import com.prize.lottery.domain.share.model.N3ComRecommendDo;
import com.prize.lottery.enums.ChartType;
import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.utils.LottoAnalyzeUtil;
import com.prize.lottery.mapper.Fc3dIcaiMapper;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.fc3d.Fc3dLottoCensusPo;
import com.prize.lottery.po.lottery.LotteryInfoPo;
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
public class Fc3dComRecommendAnaExe {

    private final Fc3dIcaiMapper           fc3dIcaiMapper;
    private final LotteryInfoMapper        lotteryInfoMapper;
    private final IFc3dRecommendRepository recommendRepository;

    public DanKillCalcResult calculate(String period, ChartType type) {
        List<String>             channels   = Lists.newArrayList(Fc3dChannel.KILL1.getChannel(), Fc3dChannel.KILL2.getChannel());
        List<Fc3dLottoCensusPo>  censusList = fc3dIcaiMapper.getVipOrItemCensusList(period, type.getType(), channels);
        Fc3dPickedAutoCalculator calculator = new Fc3dPickedAutoCalculator(censusList);
        calculator.calculate();
        DanKillCalcResult result = new DanKillCalcResult();
        result.setKWeight(calculator.getKWeight());
        result.setDanList(calculator.getDanList());
        result.setKillList(calculator.getKillList());
        return result;
    }

    public Fc3dDkRecommendVo execute(DkRecommendCmd cmd) {
        String            period        = cmd.getPeriod();
        DanKillCalcResult danKillResult = this.calculate(period, ChartType.ITEM_CHART);
        if (CollectionUtils.isEmpty(danKillResult.getDanList())) {
            return Fc3dDkRecommendVo.empty(period);
        }
        String                 last  = PeriodCalculator.fc3dPeriod(period, 1);
        List<ICaiRankedDataVo> datas = fc3dIcaiMapper.getFc3dComparedDatas(Fc3dChannel.COM7.value(), period, last, 20);
        if (CollectionUtils.isEmpty(datas)) {
            return Fc3dDkRecommendVo.empty(period);
        }
        Set<String> killList = Sets.newHashSet(danKillResult.getKillList());
        if (!CollectionUtils.isEmpty(cmd.getKills())) {
            killList.addAll(cmd.getKills());
        }
        if (!CollectionUtils.isEmpty(cmd.getExcludes())) {
            cmd.getExcludes().forEach(killList::remove);
        }
        N3ComRecommendDo recommendDo = calcRecommend(period, datas, danKillResult.getDanList(), Lists.newArrayList(killList), cmd.getKuas());
        return Fc3dDkRecommendVo.fromDo(recommendDo, danKillResult.getKWeight());
    }

    /**
     * 组选7码选码计算
     */
    public Fc3dComRecommendVo execute(Fc3dComSelectCmd command) {
        String                 last  = LotteryEnum.FC3D.lastPeriod(command.getPeriod());
        List<ICaiRankedDataVo> datas = fc3dIcaiMapper.getFc3dComparedDatas(Fc3dChannel.COM7.value(), command.getPeriod(), last, 20);
        if (CollectionUtils.isEmpty(datas)) {
            return Fc3dComRecommendVo.empty(command.getPeriod());
        }
        List<String>     dans        = command.getDans(), kills = command.getKills();
        List<Integer>    kuas        = command.getKuas();
        N3ComRecommendDo recommendDo = this.calcRecommend(command.getPeriod(), datas, dans, kills, kuas);
        if (command.getSave()) {
            recommendRepository.save(recommendDo);
        }
        return Fc3dComRecommendVo.fromDo(recommendDo);
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
        CompletableFuture<Set<String>> zu6Future = CompletableFuture.supplyAsync(() -> LottoAnalyzeUtil.n3ComAnalyze(lists, dans, kuas, 3, 3, 10));
        CompletableFuture<Set<String>> zu3Future = CompletableFuture.supplyAsync(() -> LottoAnalyzeUtil.n3ComAnalyze(lists, dans, kuas, 2, 7, 12));
        CompletableFuture<N3ComRecommendDo> future = zu6Future.thenCombine(zu3Future, (zu6, zu3) -> {
            ComRecommend zu6Rec = ComRecommend.zu6(Lists.newArrayList(zu6));
            ComRecommend zu3Rec = ComRecommend.zu3(Lists.newArrayList(zu3));
            N3ComRecommendDo recommendDo = new N3ComRecommendDo(period, zu6Rec, zu3Rec);
            //保存胆码预警
            recommendDo.addDanWarning(dans);
            //保存杀码预警
            recommendDo.addKillWarning(kills);
            //已开奖计算组合推荐命中
            LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(LotteryEnum.FC3D.getType(), period);
            if (lottery != null) {
                recommendDo.calcHit(lottery.redBalls());
            }
            return recommendDo;
        });
        return future.join();
    }

    public Pair<List<String>, List<String>> calcRecommend(String period,
                                                          List<String> danList,
                                                          List<Integer> kuaList,
                                                          List<Integer> sumList) {
        String                 last  = LotteryEnum.FC3D.lastPeriod(period);
        List<ICaiRankedDataVo> datas = fc3dIcaiMapper.getFc3dComparedDatas(Fc3dChannel.COM7.value(), period, last, 20);
        if (CollectionUtils.isEmpty(datas)) {
            return Pair.of(Lists.newArrayList(), Lists.newArrayList());
        }
        List<List<String>> lists = datas.stream()
                                        .map(e -> Lists.newArrayList(e.getData().split()))
                                        .filter(e -> LottoAnalyzeUtil.filterSum(e, sumList))
                                        .filter(e -> LottoAnalyzeUtil.isContainDans(e, danList))
                                        .collect(Collectors.toList());
        CompletableFuture<Set<String>>                      zu6Future = CompletableFuture.supplyAsync(() -> LottoAnalyzeUtil.n3ComAnalyze(lists, danList, kuaList, 3, 3, 10));
        CompletableFuture<Set<String>>                      zu3Future = CompletableFuture.supplyAsync(() -> LottoAnalyzeUtil.n3ComAnalyze(lists, danList, kuaList, 2, 7, 12));
        CompletableFuture<Pair<List<String>, List<String>>> combine   = zu6Future.thenCombine(zu3Future, (zu6, zu3) -> Pair.of(Lists.newArrayList(zu6), Lists.newArrayList(zu3)));
        return combine.join();
    }

}
