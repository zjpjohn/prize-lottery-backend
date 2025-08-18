package com.prize.lottery.po.lottery;

import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.LottoIndex;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LotteryIndexPo {

    private Long          id;
    private LotteryEnum   lottery;
    private String        period;
    private Integer       type;
    private LottoIndex    redBall;
    private LottoIndex    blueBall;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

    public void sortIndex() {
        if (redBall != null) {
            redBall.sort();
        }
        if (blueBall != null) {
            blueBall.sort();
        }
    }
}
