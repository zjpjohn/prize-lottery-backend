package com.prize.lottery.po.fc3d;

import com.prize.lottery.enums.Fc3dChannel;
import com.prize.lottery.value.ForecastValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Fc3dIcaiPo {

    private Long          id;
    private String        masterId;
    private String        period;
    private ForecastValue dan1;
    private ForecastValue dan2;
    private ForecastValue dan3;
    private ForecastValue com5;
    private ForecastValue com6;
    private ForecastValue com7;
    private ForecastValue kill1;
    private ForecastValue kill2;
    private ForecastValue comb3;
    private ForecastValue comb4;
    private ForecastValue comb5;
    private Integer       mark;
    private LocalDateTime calcTime;
    private LocalDateTime gmtCreate;


    public void calcHit(List<String> lottery) {
        for (Fc3dChannel channel : Fc3dChannel.values()) {
            channel.calcHit(channel.forecastValue(this), lottery);
        }
    }
}
