package com.prize.lottery.application.vo;

import com.prize.lottery.po.lottery.LotteryAwardPo;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.po.lottery.LotteryLevelPo;
import lombok.Data;

import java.util.List;


@Data
public class LotteryInfoVo {

    /**
     * 开奖信息
     */
    private LotteryInfoPo        lottery;
    /**
     * 奖池信息
     */
    private LotteryAwardPo       award;
    /**
     * 获奖等级信息
     */
    private List<LotteryLevelPo> levels;

}
