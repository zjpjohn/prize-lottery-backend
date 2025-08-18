package com.prize.lottery.domain.omit.ability;

import com.cloud.arch.web.utils.Assert;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.prize.lottery.domain.omit.model.LotteryDanDo;
import com.prize.lottery.domain.omit.repository.ILotteryDanRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.error.handle.ResponseHandler;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class LotteryDanAbility {

    private final LotteryInfoMapper     mapper;
    private final ILotteryDanRepository repository;

    /**
     * 初始加载并计算
     */
    public void initAndLoad(LotteryEnum type) {
        LotteryDanDo init = Optional.ofNullable(repository.latest(type)).orElseGet(() -> LotteryDanDo.load(type));

        List<LotteryInfoPo> lotteries = mapper.getLotteryInfoGtPeriod(type.getType(), init.getPeriod());
        Assert.state(!CollectionUtils.isEmpty(lotteries), ResponseHandler.NO_OPEN_LOTTERY);

        List<LotteryDanDo> danList = Lists.newArrayList();
        LotteryDanDo       last    = init;
        danList.add(last);
        for (LotteryInfoPo lottery : lotteries) {
            List<Integer> balls = lottoBalls(lottery.getRed());
            //计算上一期命中
            last.calcLastHit(balls);
            //计算下一期预测
            last = last.next(balls, lottery.getPeriod());
            //加入到集合中
            danList.add(last);
        }
        repository.saveBatch(danList);
    }

    /**
     * 计算指定期开奖号的下一期胆码
     */
    public void calculate(LotteryEnum type, String period) {
        String       lastPeriod = type.lastPeriod(period);
        LotteryDanDo lotteryDan = repository.of(type, lastPeriod);
        if (lotteryDan != null) {
            LotteryInfoPo lottery = mapper.getLotteryInfo(type.getType(), period);
            List<Integer> balls   = lottoBalls(lottery.getRed());

            List<LotteryDanDo> danList = Lists.newArrayList(lotteryDan);
            //计算上一期胆码命中请情况
            lotteryDan.calcLastHit(balls);
            //计算出下一期预测胆码
            danList.add(lotteryDan.next(balls, period));
            repository.saveBatch(danList);
        }
    }

    private List<Integer> lottoBalls(String lotto) {
        return Splitter.on(Pattern.compile("\\s+"))
                       .splitToStream(lotto)
                       .map(Integer::parseInt)
                       .collect(Collectors.toList());
    }
}
