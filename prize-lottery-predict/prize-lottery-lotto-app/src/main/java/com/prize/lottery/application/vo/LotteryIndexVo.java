package com.prize.lottery.application.vo;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.po.lottery.LotteryInfoPo;
import com.prize.lottery.value.LottoIndex;
import lombok.Data;

@Data
public class LotteryIndexVo {

    //彩种类型
    private LotteryEnum type;
    //开奖信息
    private Lottery    lottery;
    //红球指数
    private LottoIndex redBall;
    //蓝球指数
    private LottoIndex blueBall;
    //当前期号
    private String      period;

    public void setLotteryInfo(LotteryInfoPo lottery) {
        if (lottery != null) {
            this.lottery = Lottery.of(lottery);
        }
    }

    @Data
    public static class Lottery {
        private String period;
        private String red;
        private String blue;

        public static Lottery of(LotteryInfoPo value) {
            Lottery lottery = new Lottery();
            lottery.setRed(value.getRed());
            lottery.setBlue(value.getBlue());
            lottery.setPeriod(value.getPeriod());
            return lottery;
        }
    }

}
