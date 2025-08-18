package com.prize.lottery.po.trace;

import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.value.ForecastValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class No3TraceResultPo {

    private Long          id;
    private String        period;
    private ForecastValue com;
    private ForecastValue kill;
    private LocalDateTime calcTime;
    private LocalDateTime gmtCreate;

    public void calcHit(List<String> lottery) {
        Fc3dChannel.DAN1.calcHit(com, lottery);
        Fc3dChannel.KILL1.calcHit(kill, lottery);
    }
}
