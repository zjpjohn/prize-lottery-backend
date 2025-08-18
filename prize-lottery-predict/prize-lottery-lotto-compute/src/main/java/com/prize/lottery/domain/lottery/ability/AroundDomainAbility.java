package com.prize.lottery.domain.lottery.ability;

import com.cloud.arch.aggregate.AggregateFactory;
import com.cloud.arch.utils.CollectionUtils;
import com.google.common.collect.Lists;
import com.prize.lottery.application.cmd.AroundBatchCmd;
import com.prize.lottery.application.cmd.AroundSingleCmd;
import com.prize.lottery.domain.lottery.model.LotteryAroundDo;
import com.prize.lottery.domain.lottery.repository.IAroundRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AroundDomainAbility {

    private final IAroundRepository repository;
    private final LotteryInfoMapper lotteryMapper;

    /**
     * 添加绕胆图
     */
    public void addAround(AroundSingleCmd command) {
        LotteryAroundDo around  = new LotteryAroundDo(command.getPeriod(), command.getType(), command.getLotto(), command.getLottoDate(), command.getCells());
        LotteryInfoPo   lottery = lotteryMapper.getLotteryInfo(command.getLotto().getType(), command.getPeriod());
        if (lottery != null) {
            around.calcResult(lottery.redBalls());
        }
        AggregateFactory.create(around).save(repository::save);
    }

    /**
     * 计算绕胆图命中情况
     */
    public void calcAroundResult(LotteryEnum type, String period) {
        LotteryInfoPo lotteryInfo = lotteryMapper.getLotteryInfo(type.getType(), period);
        Optional.ofNullable(lotteryInfo)
                .flatMap(lottery -> repository.of(period, type)
                                              .map(agg -> agg.peek(root -> root.calcResult(lottery.redBalls()))))
                .ifPresent(repository::save);
    }

    /**
     * 批量添加绕胆图
     */
    public void addAroundList(AroundBatchCmd command) {
        List<String> periods = Lists.newArrayList();
        List<LotteryAroundDo> aroundList = command.getItems().stream().map(e -> {
            periods.add(e.getPeriod());
            return new LotteryAroundDo(e.getPeriod(), command.getType(), command.getLotto(), e.getLottoDate(), e.getCells());
        }).collect(Collectors.toList());
        List<LotteryInfoPo>        lotteryList = lotteryMapper.getLotteriesByPeriods(command.getLotto(), periods);
        Map<String, LotteryInfoPo> lotteryMap  = CollectionUtils.toMap(lotteryList, LotteryInfoPo::getPeriod);
        aroundList.forEach(around -> Optional.ofNullable(lotteryMap.get(around.getPeriod()))
                                             .ifPresent(lottery -> around.calcResult(lottery.redBalls())));
        repository.saveBatch(aroundList);
    }

}
