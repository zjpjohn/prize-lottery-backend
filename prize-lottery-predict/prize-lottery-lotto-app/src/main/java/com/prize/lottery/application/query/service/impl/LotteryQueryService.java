package com.prize.lottery.application.query.service.impl;

import com.cloud.arch.page.Page;
import com.cloud.arch.page.PageCondition;
import com.cloud.arch.page.PageQuery;
import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Maps;
import com.prize.lottery.application.assembler.LotteryAssembler;
import com.prize.lottery.application.query.dto.*;
import com.prize.lottery.application.query.service.ILotteryQueryService;
import com.prize.lottery.application.vo.*;
import com.prize.lottery.enums.CodeType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.commons.WensFilterResult;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import com.prize.lottery.infrast.utils.WensFilterCalculator;
import com.prize.lottery.mapper.*;
import com.prize.lottery.po.lottery.*;
import com.prize.lottery.utils.LotteryUtils;
import com.prize.lottery.value.Period;
import com.prize.lottery.vo.Num3ComCountVo;
import com.prize.lottery.vo.Num3LottoFollowVo;
import com.prize.lottery.vo.pl5.Pl5ItemOmitVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LotteryQueryService implements ILotteryQueryService {

    private static final String CURRENT     = "current";
    private static final String LAST        = "last";
    private static final String BEFORE      = "before";
    private static final String LAST_BEFORE = "lastBefore";

    private final LotteryInfoMapper    lotteryInfoMapper;
    private final LotteryIndexMapper   lotteryIndexMapper;
    private final LotteryAssembler     lotteryAssembler;
    private final LotteryDanMapper     lotteryDanMapper;
    private final Fc3dIcaiMapper       fc3dIcaiMapper;
    private final Pl3IcaiMapper        pl3IcaiMapper;
    private final Num3LottoIndexMapper num3IndexMapper;

    @Override
    public String latestPeriod(LotteryEnum type) {
        return lotteryInfoMapper.latestPeriod(type.getType());
    }

    @Override
    public List<String> lotteryPeriods(LotteryEnum type, Integer limit) {
        return lotteryInfoMapper.getLotteryPeriods(type, limit);
    }

    @Override
    public Page<LotteryInfoPo> getLotteryInfoByPage(LotteryListQuery query) {
        return query.from().count(lotteryInfoMapper::countLotteryList).query(lotteryInfoMapper::getLotteryList);
    }

    @Override
    public Map<String, LotteryInfoPo> lotteryFastTable(String type, String period) {
        LotteryEnum lottery = LotteryEnum.findOf(type);
        Assert.state(lottery.fastTable(), ResponseHandler.FAST_TABLE_CLOSED);

        PageCondition condition = new PageCondition().setLimit(4)
                                                     .setParam("shiNotNull", 1)
                                                     .setParam("type", lottery.getType());
        if (StringUtils.isNotBlank(period)) {
            condition.setParam("period", period);
        }
        List<LotteryInfoPo> lotteries = lotteryInfoMapper.getLotteryList(condition);
        Assert.state(lotteries != null && lotteries.size() > 3, ResponseHandler.FAST_TABLE_ERROR);
        Map<String, LotteryInfoPo> data = Maps.newHashMap();
        data.put(CURRENT, lotteries.get(0));
        data.put(LAST, lotteries.get(1));
        data.put(BEFORE, lotteries.get(2));
        data.put(LAST_BEFORE, lotteries.get(3));
        return data;
    }

    @Override
    public LotteryInfoVo getLotteryDetail(String type, String period) {
        LotteryEnum lottery = LotteryEnum.findOf(type);

        LotteryInfoPo lotteryInfo = lotteryInfoMapper.getLotteryInfo(lottery.getType(), period);
        Assert.notNull(lotteryInfo, ResponseHandler.LOTTERY_INFO_ERROR);

        LotteryInfoVo detail = new LotteryInfoVo();
        detail.setLottery(lotteryInfo);

        LotteryAwardPo award = lotteryInfoMapper.getLotteryAwardByUk(lottery.getType(), period);
        detail.setAward(award);

        List<LotteryLevelPo> levels = lotteryInfoMapper.getLotteryLevels(lottery.getType(), period);
        detail.setLevels(levels);
        return detail;
    }

    @Override
    public List<LotteryInfoPo> getLatestGroupsLotteries() {
        return lotteryInfoMapper.getNewestGroupLotteries()
                                .stream()
                                .filter(e -> !LotteryEnum.PL5.getType().equals(e.getType()))
                                .collect(Collectors.toList());
    }

    @Override
    public List<LotteryInfoPo> getLatestGroupsV1Lotteries() {
        return lotteryInfoMapper.getNewestGroupLotteries();
    }

    @Override
    public List<LotteryInfoPo> getTypedLatestLotteries(List<String> types) {
        return lotteryInfoMapper.getNewestLotteriesByTypes(types);
    }

    @Override
    public List<LotteryInfoPo> getLatestLimitLotteries(LotteryEnum type, Integer limit) {
        return lotteryInfoMapper.getLatestLimitLotteries(type.getType(), limit);
    }

    @Override
    public LotteryInfoPo getLatestLottery(LotteryEnum lottery) {
        return lotteryInfoMapper.getNewestLottery(lottery.getType());
    }

    @Override
    public List<LotteryOmitPo> getLotteryOmitList(LotteryEnum lottery, Integer size) {
        Assert.state(lottery != LotteryEnum.KL8, ResponseHandler.LOTTERY_TYPE_ERROR);
        PageCondition condition = new PageCondition().setParam("type", lottery.getType()).setLimit(size);
        return lotteryInfoMapper.getLotteryOmitList(condition);
    }

    @Override
    public List<LotterySumOmitPo> getSumOmitList(LotteryEnum type, Integer size) {
        Assert.state(type == LotteryEnum.PL3
                             || type == LotteryEnum.FC3D
                             || type == LotteryEnum.PL5, ResponseHandler.LOTTERY_TYPE_ERROR);
        return lotteryInfoMapper.getSumOmitList(type.getType(), size);
    }

    @Override
    public List<LotteryKuaOmitPo> getKuaOmitList(LotteryEnum type, Integer size) {
        Assert.state(type == LotteryEnum.PL3
                             || type == LotteryEnum.FC3D
                             || type == LotteryEnum.PL5, ResponseHandler.LOTTERY_TYPE_ERROR);
        return lotteryInfoMapper.getKuaOmitList(type.getType(), size);
    }

    @Override
    public List<LotteryTrendOmitPo> getTrendOmitList(LotteryEnum type, Integer size) {
        Assert.state(type == LotteryEnum.PL3 || type == LotteryEnum.FC3D, ResponseHandler.LOTTERY_TYPE_ERROR);
        return lotteryInfoMapper.getLotteryTrendOmits(type, size);
    }

    @Override
    public List<LotteryMatchOmitPo> getMatchOmitList(LotteryEnum type, Integer size) {
        Assert.state(type == LotteryEnum.PL3 || type == LotteryEnum.FC3D, ResponseHandler.LOTTERY_TYPE_ERROR);
        return lotteryInfoMapper.getLotteryMatchOmits(type, size);
    }

    @Override
    public List<LotteryItemOmitPo> getItemOmitList(LotteryEnum type, Integer size) {
        Assert.state(type == LotteryEnum.PL3 || type == LotteryEnum.FC3D, ResponseHandler.LOTTERY_TYPE_ERROR);
        return lotteryInfoMapper.getLotteryItemOmits(type, size);
    }

    @Override
    public List<LotteryKl8OmitPo> getKl8OmitList() {
        return lotteryInfoMapper.getKl8OmitList(30);
    }

    @Override
    public List<LotteryKl8OmitPo> getKl8TailOmits(Integer limit) {
        return lotteryInfoMapper.getKl8TailOmits(limit);
    }

    @Override
    public Page<LotteryKl8OmitPo> getKl8BaseOmits(PageQuery query) {
        return query.from().count(lotteryInfoMapper::countKl8Omits).query(lotteryInfoMapper::getKl8BaseOmitList);
    }

    @Override
    public Page<LotteryIndexPo> getLotteryIndexList(LotteryIndexQuery query) {
        return query.from()
                    .count(lotteryIndexMapper::countLotteryIndices)
                    .query(lotteryIndexMapper::getLotteryIndexList)
                    .forEach(LotteryIndexPo::sortIndex);
    }

    @Override
    public List<LotteryCodeVo> getLotteryCodeList(LotteryEnum lotto, CodeType type) {
        List<LotteryCodePo> codes = lotteryInfoMapper.getLottoCodesByType(lotto, type, 50);
        if (CollectionUtils.isEmpty(codes)) {
            return Collections.emptyList();
        }
        //构建万能码信息
        List<LotteryCodeVo> codeList = joinConverter(lotto, codes, LotteryCodePo::getPeriod, (code, lottery) -> {
            LotteryCodeVo codeVo = lotteryAssembler.toVo(code);
            Optional.ofNullable(lottery).ifPresent(lo -> codeVo.setLottery(lo.getRed()));
            return codeVo;
        });
        //按期号递增排序
        return codeList.stream().sorted(Comparator.comparing(LotteryCodeVo::getPeriod)).collect(Collectors.toList());
    }

    @Override
    public Page<LotteryCodeVo> getLotteryCodeList(LotteryCodeQuery query) {
        return query.from()
                    .count(lotteryInfoMapper::countLotteryCodes)
                    .query(lotteryInfoMapper::getLotteryCodeList)
                    .flatMap(codes -> joinConverter(query.getLottery(), codes, LotteryCodePo::getPeriod, (code, lottery) -> {
                        LotteryCodeVo codeVo = lotteryAssembler.toVo(code);
                        Optional.ofNullable(lottery).ifPresent(lotto -> codeVo.setLottery(lotto.getRed()));
                        return codeVo;
                    }));
    }

    @Override
    public List<LotteryDanVo> getLotteryDanList(LotteryEnum type) {
        List<LotteryDanPo> danList = lotteryInfoMapper.getLotteryDanList(type, 50);
        if (CollectionUtils.isEmpty(danList)) {
            return Collections.emptyList();
        }
        List<LotteryDanVo> voList = joinConverter(type, danList, LotteryDanPo::getPeriod, (dan, lottery) -> {
            LotteryDanVo danVo = lotteryAssembler.toVo(dan);
            Optional.ofNullable(lottery).ifPresent(lotto -> danVo.setLottery(lotto.getRed()));
            return danVo;
        });
        return voList.stream().sorted(Comparator.comparing(LotteryDanVo::getPeriod)).collect(Collectors.toList());
    }

    @Override
    public List<LotteryOttVo> getLotteryOttList(LotteryEnum type, Integer size) {
        List<LotteryOttPo> ottList = lotteryInfoMapper.getLotteryOttList(type, size);
        if (CollectionUtils.isEmpty(ottList)) {
            return Collections.emptyList();
        }
        List<LotteryOttVo> voList = joinConverter(type, ottList, LotteryOttPo::getPeriod, (ott, lottery) -> {
            LotteryOttVo ottVo = lotteryAssembler.toVo(ott);
            Optional.ofNullable(lottery).ifPresent(lotto -> ottVo.setLottery(lotto.getRed()));
            return ottVo;
        });
        return voList.stream().sorted(Comparator.comparing(LotteryOttVo::getPeriod)).collect(Collectors.toList());
    }

    private <T, V> List<V> joinConverter(LotteryEnum type,
                                         List<T> dataList,
                                         Function<T, String> periodFunc,
                                         BiFunction<T, LotteryInfoPo, V> converter) {
        List<String> periods = CollectionUtils.toList(dataList, periodFunc);
        Collections.sort(periods);
        List<LotteryInfoPo> lotteries = lotteryInfoMapper.getLotteriesBetweenPeriods(type, periods.get(0), periods.get(
                periods.size()
                        - 1));
        Map<String, LotteryInfoPo> map = CollectionUtils.toMap(lotteries, LotteryInfoPo::getPeriod);
        return dataList.stream()
                       .map(t -> converter.apply(t, map.get(periodFunc.apply(t))))
                       .collect(Collectors.toList());
    }

    @Override
    public Page<LotteryAroundPo> getAroundList(LottoAroundQuery query) {
        return query.from().count(lotteryDanMapper::countLotteryArounds).query(lotteryDanMapper::getLotteryAroundList);
    }

    @Override
    public Page<LotteryHoneyPo> getHoneyList(LottoHoneyQuery query) {
        return query.from().count(lotteryDanMapper::countLotteryHoneys).query(lotteryDanMapper::getLotteryHoneyList);
    }

    @Override
    public List<String> aroundPeriods(LotteryEnum type) {
        return lotteryDanMapper.getAroundPeriods(type);
    }

    @Override
    public List<String> honeyPeriods(LotteryEnum type) {
        return lotteryDanMapper.getHoneyPeriods(type);
    }

    @Override
    public LotteryAroundVo lotteryAround(String period, LotteryEnum type) {
        if (StringUtils.isBlank(period)) {
            period = lotteryDanMapper.latestAroundPeriod(type);
            Assert.notNull(period, ResponseHandler.AROUND_NONE);
        }
        LotteryAroundPo around = lotteryDanMapper.getLotteryAround(type, period)
                                                 .orElseThrow(Assert.supply(ResponseHandler.AROUND_NONE));
        LotteryInfoPo   lottery  = lotteryInfoMapper.getLotteryInfo(type.getType(), period);
        LotteryAroundVo aroundVo = lotteryAssembler.toVo(around);
        if (lottery != null) {
            aroundVo.setBalls(lottery.redBalls());
        }
        return aroundVo;
    }

    @Override
    public LotteryHoneyVo lotteryHoney(String period, LotteryEnum type) {
        if (StringUtils.isBlank(period)) {
            period = lotteryDanMapper.latestHoneyPeriod(type);
            Assert.notNull(period, ResponseHandler.HONEY_NONE);
        }
        LotteryHoneyPo honey = lotteryDanMapper.getLotteryHoney(type, period)
                                               .orElseThrow(Assert.supply(ResponseHandler.HONEY_NONE));
        LotteryInfoPo  lottery = lotteryInfoMapper.getLotteryInfo(type.getType(), period);
        LotteryHoneyVo honeyVo = lotteryAssembler.toVo(honey);
        if (lottery != null) {
            honeyVo.setBalls(lottery.redBalls());
        }
        return honeyVo;
    }

    @Override
    public WensFilterResult filterNum3Lotto(WensFilterQuery query) {
        LotteryEnum type = query.getType();
        Assert.state(type == LotteryEnum.FC3D || type == LotteryEnum.PL3, ResponseHandler.LOTTERY_TYPE_ERROR);
        String        period  = query.getPeriod();
        LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(type.getType(), period);
        Assert.notNull(lottery, ResponseHandler.LOTTERY_INFO_ERROR);
        List<String> balls = lottery.redBalls();
        //稳氏过滤计算
        WensFilterResult result = WensFilterCalculator.filter(balls, query.getDan(), query.getKillList(), query.getKuaList(), query.sumRange());
        result.setPeriod(period);
        result.setLottery(lottery.getRed());
        //如果下一期已开奖，计算过滤命中
        String        nextPeriod = type.nextPeriod(period);
        LotteryInfoPo next       = lotteryInfoMapper.getLotteryInfo(type.getType(), nextPeriod);
        if (next != null) {
            String nextLotto = next.redBalls().stream().sorted().collect(Collectors.joining(""));
            result.calcHit(nextLotto);
            result.setNext(next.getRed());
        }
        return result;
    }

    @Override
    public Num3LotteryVo getNum3Lottery(LotteryEnum type, String period) {
        Assert.state(type == LotteryEnum.FC3D || type == LotteryEnum.PL3, ResponseHandler.LOTTERY_TYPE_ERROR);
        if (StringUtils.isBlank(period)) {
            period = lotteryInfoMapper.latestPeriod(type.getType());
        }
        LotteryInfoPo current = lotteryInfoMapper.getLotteryInfo(type.getType(), period);
        Assert.notNull(current, ResponseHandler.LOTTERY_INFO_ERROR);
        String        nextPeriod = type.nextPeriod(period);
        Num3LotteryVo result     = new Num3LotteryVo();
        LotteryInfoPo next       = lotteryInfoMapper.getLotteryByUk(type.getType(), nextPeriod);
        if (next != null) {
            result.setNext(next.getRed());
            result.setNextShi(next.getShi());
        }
        result.setPeriod(period);
        result.setNextPeriod(nextPeriod);
        result.setCurrent(current.getRed());
        result.setLotDate(current.getLotDate());
        return result;
    }

    @Override
    public Num3LotteryVo getNum3LastLottery(LotteryEnum type, String period) {
        Assert.state(type == LotteryEnum.FC3D || type == LotteryEnum.PL3, ResponseHandler.LOTTERY_TYPE_ERROR);
        if (StringUtils.isBlank(period)) {
            period = latestN3Period(type).getPeriod();
        }
        String        lastPeriod = type.lastPeriod(period);
        LotteryInfoPo last       = lotteryInfoMapper.getLotteryInfo(type.getType(), lastPeriod);
        Assert.notNull(last, ResponseHandler.LOTTERY_INFO_ERROR);
        Num3LotteryVo result = new Num3LotteryVo();
        result.setPeriod(lastPeriod);
        result.setNextPeriod(period);
        result.setCurrent(last.getRed());
        result.setLotDate(last.getLotDate());
        LotteryInfoPo next = lotteryInfoMapper.getLotteryByUk(type.getType(), period);
        if (next != null) {
            result.setNext(next.getRed());
            result.setNextShi(next.getShi());
        }
        String        before        = type.lastPeriod(lastPeriod);
        LotteryInfoPo beforeLottery = lotteryInfoMapper.getLotteryInfo(type.getType(), before);
        if (beforeLottery != null) {
            result.setLastPeriod(before);
            result.setLast(beforeLottery.getRed());
        }
        return result;
    }

    private Period latestN3Period(LotteryEnum type) {
        if (type == LotteryEnum.FC3D) {
            return fc3dIcaiMapper.latestFc3dIcaiPeriod();
        }
        return pl3IcaiMapper.latestPl3ICaiPeriod();
    }

    @Override
    public List<String> num3LotteryPeriods(LotteryEnum type) {
        return lotteryInfoMapper.num3LotteryPeriods(type, 50);
    }

    @Override
    public List<LotteryInfoPo> num3BeforeLimitLotteries(LotteryEnum type, String period, Integer limit) {
        if (StringUtils.isBlank(period)) {
            period = lotteryInfoMapper.num3LatestPeriod(type);
            Assert.state(StringUtils.isNotBlank(period), ResponseHandler.LOTTERY_INFO_ERROR);
        }
        return lotteryInfoMapper.getBeforeLimitLotteries(type.getType(), period, limit);
    }

    @Override
    public Page<Num3LottoIndexPo> getNum3IndexList(Num3IndexQuery query) {
        return query.from().count(num3IndexMapper::countLottoIndices).query(num3IndexMapper::getLottoIndices);
    }

    private Optional<LotteryInfoPo> ofLottery(List<LotteryInfoPo> lotteries, String period) {
        return lotteries.stream().filter(e -> e.getPeriod().equals(period)).findFirst();

    }

    @Override
    public List<LottoN3PianOmitVo> getN3PianOmits(LotteryEnum type, Integer limit) {
        List<LottoPianOmitPo> omitList = lotteryInfoMapper.getLottoPianList(type, null, limit);
        if (CollectionUtils.isEmpty(omitList)) {
            return Collections.emptyList();
        }
        List<LotteryInfoPo>        lotteries = lotteryInfoMapper.getLimitLotteryList(type, limit);
        Map<String, LotteryInfoPo> lottoMap  = Maps.uniqueIndex(lotteries, LotteryInfoPo::getPeriod);

        Map<String, List<LottoPianOmitPo>> grouped = omitList.stream()
                                                             .collect(Collectors.groupingBy(LottoPianOmitPo::getPeriod));
        return grouped.entrySet().stream().map(e -> {
            LottoN3PianOmitVo vo = new LottoN3PianOmitVo();
            vo.setType(type);
            vo.setPeriod(e.getKey());
            vo.leveledOmits(e.getValue());
            Optional.ofNullable(lottoMap.get(e.getKey())).map(LotteryInfoPo::getRed).ifPresent(vo::setLottery);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public Num3LottoDanVo getNum3LottoDan(LotteryEnum type, String period) {
        if (StringUtils.isBlank(period)) {
            period = lotteryInfoMapper.latestPeriod(type.getType());
            Assert.notNull(period, ResponseHandler.LOTTERY_INFO_ERROR);
        }
        List<LotteryInfoPo> lotteries = lotteryInfoMapper.getBeforeLimitLotteries(type.getType(), period, 3);
        if (CollectionUtils.isEmpty(lotteries)) {
            return Num3LottoDanVo.empty(period);
        }
        String        lastPeriod   = type.lastPeriod(period);
        String        beforePeriod = type.lastPeriod(lastPeriod);
        LotteryInfoPo last         = ofLottery(lotteries, lastPeriod).orElse(null);
        LotteryInfoPo before       = ofLottery(lotteries, beforePeriod).orElse(null);
        if (last == null || before == null) {
            return Num3LottoDanVo.empty(period);
        }
        List<Integer> danList = calcDanList(last.redBalls(), before.redBalls());
        List<Integer> shiList = calcDanList(last.shiBalls(), before.shiBalls());
        return new Num3LottoDanVo(period, danList, shiList);
    }

    private List<Integer> calcDanList(List<String> last, List<String> before) {
        if (CollectionUtils.isEmpty(last) || CollectionUtils.isEmpty(before)) {
            return Collections.emptyList();
        }
        int           lastTail   = LotteryUtils.sumTail(last);
        int           beforeTail = LotteryUtils.sumTail(before);
        int           tail       = (lastTail + beforeTail) % 10;
        int           duiMa      = LotteryUtils.duiMa(tail);
        List<Integer> danList    = LotteryUtils.neighbors(tail);
        danList.add(duiMa);
        Collections.sort(danList);
        return danList;
    }

    @Override
    public Page<LotteryFairTrialPo> fairTrialList(TrialListQuery query) {
        return query.from().count(lotteryInfoMapper::countFairTrials).query(lotteryInfoMapper::getFairTrialList);
    }

    @Override
    public List<String> trialPeriods(LotteryEnum type, Integer limit) {
        return lotteryInfoMapper.getFairTrialPeriods(type, limit);
    }

    @Override
    public Map<String, LotteryFairTrialPo> trailTable(LotteryEnum type, String period) {
        if (StringUtils.isBlank(period)) {
            period = lotteryInfoMapper.getTrialLatestPeriod(type);
        }
        Assert.notNull(period, ResponseHandler.LOTTERY_INFO_ERROR);
        List<LotteryFairTrialPo> values = lotteryInfoMapper.getFairTrialLtePeriod(type, period);
        Assert.state(CollectionUtils.isNotEmpty(values) && values.size() >= 3, ResponseHandler.FAST_TABLE_ERROR);
        Map<String, LotteryFairTrialPo> data = Maps.newHashMap();
        data.put(CURRENT, values.get(0));
        data.put(LAST, values.get(1));
        data.put(BEFORE, values.get(2));
        return data;
    }

    @Override
    public Map<Integer, List<String>> getNum3ComCounts(LotteryEnum type, Integer limit) {
        List<Num3ComCountVo> counts = lotteryInfoMapper.getNum3ComCounts(type, limit);
        if (CollectionUtils.isEmpty(counts)) {
            return Collections.emptyMap();
        }
        Map<Integer, List<String>> result = counts.stream()
                                                  .collect(Collectors.groupingBy(Num3ComCountVo::getAmount, Collectors.mapping(Num3ComCountVo::getCom, Collectors.toList())));
        result.forEach((k, v) -> Collections.sort(v));
        //计算指定期未出的组选号码
        List<String> opened = result.values().stream().flatMap(List::stream).collect(Collectors.toList());
        List<String> unOpen = LotteryUtils.unOpenLotto(opened);
        result.put(0, unOpen);
        return result;
    }

    @Override
    public List<Num3LottoFollowVo> getNum3FollowList(LotteryEnum type, String period) {
        if (StringUtils.isBlank(period)) {
            period = lotteryInfoMapper.latestPeriod(type.getType());
        }
        LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(type.getType(), period);
        Assert.notNull(lottery, ResponseHandler.LOTTERY_INFO_ERROR);
        return lotteryInfoMapper.getNum3FollowList(type, type.nextPeriod(period, 3), lottery.getCom());
    }

    @Override
    public List<Num3LottoFollowVo> getComFollowList(LotteryEnum type, String com) {
        return lotteryInfoMapper.getNum3FollowList(type, null, com);
    }

    @Override
    public List<Num3LottoFollowVo> getFilterList(LottoFilterQuery query) {
        query.validate();
        List<Num3LottoFollowVo> follows = lotteryInfoMapper.getFilterNum3Follows(query.getType(), query.getLimit());
        if (CollectionUtils.isEmpty(follows)) {
            return Collections.emptyList();
        }
        return follows.stream()
                      .filter(e -> e.filter(query.getDanList(), query.getKuaList(), query.getSumList()))
                      .collect(Collectors.toList());
    }

    @Override
    public List<LotteryPl5OmitPo> getPl5OmitList(Integer size) {
        return lotteryInfoMapper.getLotteryPl5Omits(size);
    }

    @Override
    public List<Pl5ItemOmitVo> getPl5ItemOmits(Integer type, Integer limit) {
        return lotteryInfoMapper.getPl5ItemOmits("cb" + type, limit);
    }
}
