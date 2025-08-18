package com.prize.lottery.po.trace;

import com.prize.lottery.enums.SsqChannel;
import com.prize.lottery.value.ForecastValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RbTraceResultPo {

    private Long          id;
    private String        period;
    private ForecastValue red;
    private ForecastValue rk;
    private ForecastValue blue;
    private ForecastValue bk;
    private LocalDateTime calcTime;
    private LocalDateTime gmtCreate;

    public void calcHit(List<String> reds, List<String> blues) {
        SsqChannel.RED1.calcHit(red, reds, null);
        SsqChannel.RK3.calcHit(rk, reds, null);
        SsqChannel.RED1.calcHit(blue, blues, null);
        SsqChannel.RK3.calcHit(bk, blues, null);
    }
}
