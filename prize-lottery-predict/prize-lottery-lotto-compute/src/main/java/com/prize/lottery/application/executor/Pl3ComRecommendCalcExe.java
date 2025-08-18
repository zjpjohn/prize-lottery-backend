package com.prize.lottery.application.executor;

import com.prize.lottery.domain.pl3.repository.IPl3RecommendRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Pl3ComRecommendCalcExe {

    private final LotteryInfoMapper       lotteryInfoMapper;
    private final IPl3RecommendRepository pl3RecommendRepository;

    /**
     * 计算组合推荐命中
     *
     * @param period 推荐期号
     */
    public void execute(String period) {
        LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(LotteryEnum.PL3.getType(), period);
        if (lottery != null) {
            pl3RecommendRepository.getBy(period).filter(rec -> rec.getCalcTime() == null).ifPresent(recommend -> {
                recommend.calcHit(lottery.redBalls());
                pl3RecommendRepository.save(recommend);
            });
        }

    }

}
