package com.prize.lottery.application.vo;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.value.LottoIndex;
import lombok.Data;

@Data
public class Num3LottoIndexVo {

    private LotteryEnum type;
    private String     period;
    private LottoIndex comIndex;
    private LottoIndex danIndex;
    private LottoIndex             killIndex;
    private LotteryIndexVo.Lottery lottery;

    public void setLotteryInfo(LotteryInfoPo lottery) {
        if (lottery != null) {
            this.lottery = LotteryIndexVo.Lottery.of(lottery);
        }
    }

}
