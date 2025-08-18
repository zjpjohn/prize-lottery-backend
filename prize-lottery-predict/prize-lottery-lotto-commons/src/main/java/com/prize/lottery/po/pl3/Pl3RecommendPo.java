package com.prize.lottery.po.pl3;

import com.prize.lottery.enums.Pl3Channel;
import com.prize.lottery.value.ForecastValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Pl3RecommendPo {

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
        Pl3Channel.DAN1.calcHit(this.dan1, lottery);
        Pl3Channel.DAN2.calcHit(this.dan2, lottery);
        Pl3Channel.DAN3.calcHit(this.dan3, lottery);
        Pl3Channel.COM5.calcHit(this.com5, lottery);
        Pl3Channel.COM6.calcHit(this.com6, lottery);
        Pl3Channel.COM7.calcHit(this.com7, lottery);
        Pl3Channel.KILL1.calcHit(this.kill1, lottery);
        Pl3Channel.KILL2.calcHit(this.kill2, lottery);
    }
}
