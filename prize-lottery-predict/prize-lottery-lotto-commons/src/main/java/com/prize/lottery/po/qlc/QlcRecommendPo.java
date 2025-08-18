package com.prize.lottery.po.qlc;

import com.prize.lottery.enums.QlcChannel;
import com.prize.lottery.value.ForecastValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QlcRecommendPo {

    private Long          id;
    private String        period;
    private ForecastValue red1;
    private ForecastValue red2;
    private ForecastValue red3;
    private ForecastValue red12;
    private ForecastValue red18;
    private ForecastValue red22;
    private ForecastValue kill3;
    private ForecastValue kill6;
    private Integer       state;
    private LocalDateTime calcTime;
    private LocalDateTime gmtCreate;

    public void calcHit(List<String> reds) {
        QlcChannel.RED1.calcHit(this.red1, reds);
        QlcChannel.RED2.calcHit(this.red2, reds);
        QlcChannel.RED3.calcHit(this.red3, reds);
        QlcChannel.RED12.calcHit(this.red12, reds);
        QlcChannel.RED18.calcHit(this.red18, reds);
        QlcChannel.RED22.calcHit(this.red22, reds);
        QlcChannel.RK3.calcHit(this.kill3, reds);
        QlcChannel.RK6.calcHit(this.kill6, reds);
    }
}
