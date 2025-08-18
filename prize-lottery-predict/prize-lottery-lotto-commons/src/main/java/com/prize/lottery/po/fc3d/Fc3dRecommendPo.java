package com.prize.lottery.po.fc3d;

import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.value.ForecastValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Fc3dRecommendPo {

    private Long   id;
    private String period;

    private ForecastValue dan1;
    private ForecastValue dan2;
    private ForecastValue dan3;
    private ForecastValue com5;
    private ForecastValue com6;
    private ForecastValue com7;
    private ForecastValue kill1;
    private ForecastValue kill2;

    private Integer       state;
    private LocalDateTime calcTime;
    private LocalDateTime gmtCreate;

    public void calcHit(List<String> lottery) {
        Fc3dChannel.DAN1.calcHit(this.dan1, lottery);
        Fc3dChannel.DAN2.calcHit(this.dan2, lottery);
        Fc3dChannel.DAN3.calcHit(this.dan3, lottery);
        Fc3dChannel.COM5.calcHit(this.com5, lottery);
        Fc3dChannel.COM6.calcHit(this.com6, lottery);
        Fc3dChannel.COM7.calcHit(this.com7, lottery);
        Fc3dChannel.KILL1.calcHit(this.kill1, lottery);
        Fc3dChannel.KILL2.calcHit(this.kill2, lottery);
    }
}
