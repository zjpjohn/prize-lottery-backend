package com.prize.lottery.application.executor;

import com.prize.lottery.domain.fc3d.repository.IFc3dRecommendRepository;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.mapper.LotteryInfoMapper;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Fc3dComRecommendCalcExe {

    private final LotteryInfoMapper        lotteryInfoMapper;
    private final IFc3dRecommendRepository fc3dRecommendRepository;

    /**
     * 计算组合推荐命中
     *
     * @param period 推荐期号
     */
    public void execute(String period) {
        LotteryInfoPo lottery = lotteryInfoMapper.getLotteryInfo(LotteryEnum.FC3D.getType(), period);
        if (lottery != null) {
            fc3dRecommendRepository.getBy(period).filter(rec -> rec.getCalcTime() == null).ifPresent(recommend -> {
                recommend.calcHit(lottery.redBalls());
                fc3dRecommendRepository.save(recommend);
            });
        }
    }

}
