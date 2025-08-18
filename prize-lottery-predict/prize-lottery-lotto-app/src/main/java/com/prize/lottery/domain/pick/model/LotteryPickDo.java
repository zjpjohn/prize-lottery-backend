package com.prize.lottery.domain.pick.model;

import com.prize.lottery.application.command.dto.LottoN3PickCmd;
import com.prize.lottery.application.command.dto.LottoPickCmd;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.BlueBall;
import com.prize.lottery.value.RedBall;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LotteryPickDo {

    private LotteryEnum lottery;
    private String      period;
    private Long        userId;
    private RedBall     redBall;
    private BlueBall    blueBall;

    public LotteryPickDo(LottoN3PickCmd command, Long userId) {
        this.userId  = userId;
        this.period  = command.getPeriod();
        this.lottery = LotteryEnum.findOf(command.getLottery());
        this.redBall = new RedBall(command.getBalls());
    }

    public LotteryPickDo(LottoPickCmd command, Long userId) {
        this.userId   = userId;
        this.period   = command.getPeriod();
        this.lottery  = LotteryEnum.findOf(command.getLottery());
        this.redBall  = RedBall.from(command.getReds());
        this.blueBall = new BlueBall(command.getBlues());
    }

}
