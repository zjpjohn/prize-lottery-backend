package com.prize.lottery.domain.omit.ability;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.model.LotteryOttDo;
import com.prize.lottery.domain.omit.repository.ILotteryOttRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class LotteryOttAbility {

    private final LotteryInfoMapper     mapper;
    private final ILotteryOttRepository repository;

    public void initialize(LotteryEnum type, Integer limit) {
        List<LotteryInfoPo> lotteries = mapper.getLimitLotteryList(type, limit);
        List<LotteryOttDo> ottList = lotteries.stream().map(lottery -> {
            List<Integer> balls = lottoBalls(lottery.getRed());
            return new LotteryOttDo(type, lottery.getPeriod(), balls);
        }).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(ottList)) {
            repository.saveBatch(ottList);
        }
    }

    public void execute(LotteryEnum type, String period) {
        LotteryInfoPo lottery    = mapper.getLotteryInfo(type.getType(), period);
        List<Integer> balls      = lottoBalls(lottery.getRed());
        LotteryOttDo  lotteryOtt = new LotteryOttDo(type, lottery.getPeriod(), balls);
        repository.saveBatch(Lists.newArrayList(lotteryOtt));
    }

    private List<Integer> lottoBalls(String lotto) {
        return Splitter.on(Pattern.compile("\\s+"))
                       .splitToStream(lotto)
                       .map(Integer::parseInt)
                       .collect(Collectors.toList());
    }

}
