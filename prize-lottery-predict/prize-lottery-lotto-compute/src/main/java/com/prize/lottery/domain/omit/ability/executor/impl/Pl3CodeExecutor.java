package com.prize.lottery.domain.omit.ability.executor.impl;

import com.cloud.arch.utils.CollectionUtils;
import com.prize.lottery.domain.omit.ability.executor.CodeExecutor;
import com.prize.lottery.domain.omit.model.LotteryCodeDo;
import com.prize.lottery.domain.omit.repository.ILotteryCodeRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class Pl3CodeExecutor implements CodeExecutor {

    private final LotteryInfoMapper      mapper;
    private final ILotteryCodeRepository repository;

    @Override
    public LotteryEnum bizIndex() {
        return LotteryEnum.PL3;
    }

    @Override
    public void initialize(Integer limit) {
        List<LotteryInfoPo> lotteries = mapper.getLimitLotteryList(this.bizIndex(), limit);
        List<LotteryCodeDo> codeList = lotteries.stream().flatMap(lottery -> {
            return LotteryCodeDo.pl3(lottery.getPeriod(), lottery.getRed()).stream();
        }).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(codeList)) {
            repository.saveBatch(codeList);
        }
    }

    @Override
    public void execute(String period) {
        Optional.ofNullable(mapper.getLotteryInfo(this.bizIndex().getType(), period))
                .map(lottery -> LotteryCodeDo.pl3(period, lottery.getRed()))
                .ifPresent(repository::saveBatch);
    }

}
