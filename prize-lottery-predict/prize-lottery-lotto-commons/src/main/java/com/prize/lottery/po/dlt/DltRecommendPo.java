package com.prize.lottery.po.dlt;

import com.prize.lottery.enums.DltChannel;
import com.prize.lottery.value.ForecastValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DltRecommendPo {

    private Long   id;
    private String period;

    private ForecastValue red1;
    private ForecastValue red2;
    private ForecastValue red3;
    private ForecastValue red10;
    private ForecastValue red20;
    private ForecastValue redKill3;
    private ForecastValue redKill6;
    private ForecastValue blue1;
    private ForecastValue blue2;
    private ForecastValue blue6;
    private ForecastValue blueKill3;

    private Integer       state;
    private LocalDateTime calcTime;
    private LocalDateTime gmtCreate;

    public void calcHit(List<String> reds, List<String> blues) {
        DltChannel.RED1.calcHit(this.red1, reds, blues);
        DltChannel.RED2.calcHit(this.red2, reds, blues);
        DltChannel.RED3.calcHit(this.red3, reds, blues);
        DltChannel.RED10.calcHit(this.red10, reds, blues);
        DltChannel.RED20.calcHit(this.red20, reds, blues);
        DltChannel.RK3.calcHit(this.redKill3, reds, blues);
        DltChannel.RK6.calcHit(this.redKill6, reds, blues);
        DltChannel.BLUE1.calcHit(this.blue1, reds, blues);
        DltChannel.BLUE2.calcHit(this.blue2, reds, blues);
        DltChannel.BLUE6.calcHit(this.blue6, reds, blues);
        DltChannel.BK.calcHit(this.blueKill3, reds, blues);
    }
}
