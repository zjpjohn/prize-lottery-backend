package com.prize.lottery.po.ssq;

import com.prize.lottery.enums.SsqChannel;
import com.prize.lottery.value.ForecastValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SsqRecommendPo {

    private Long   id;
    private String period;

    private ForecastValue red1;
    private ForecastValue red2;
    private ForecastValue red3;
    private ForecastValue red12;
    private ForecastValue red20;
    private ForecastValue red25;
    private ForecastValue redKill3;
    private ForecastValue redKill6;
    private ForecastValue blue3;
    private ForecastValue blue5;
    private ForecastValue blueKill;

    private Integer       state;
    private LocalDateTime calcTime;
    private LocalDateTime gmtCreate;

    public void calcHit(List<String> reds, String blue) {
        SsqChannel.RED1.calcHit(this.red1, reds, blue);
        SsqChannel.RED2.calcHit(this.red2, reds, blue);
        SsqChannel.RED3.calcHit(this.red3, reds, blue);
        SsqChannel.RED12.calcHit(this.red12, reds, blue);
        SsqChannel.RED20.calcHit(this.red20, reds, blue);
        SsqChannel.RED25.calcHit(this.red25, reds, blue);
        SsqChannel.RK3.calcHit(this.redKill3, reds, blue);
        SsqChannel.RK6.calcHit(this.redKill6, reds, blue);
        SsqChannel.BLUE3.calcHit(this.blue3, reds, blue);
        SsqChannel.BLUE5.calcHit(this.blue5, reds, blue);
        SsqChannel.BK.calcHit(this.blueKill, reds, blue);
    }
}
