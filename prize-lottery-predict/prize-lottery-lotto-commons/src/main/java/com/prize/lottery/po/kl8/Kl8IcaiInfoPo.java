package com.prize.lottery.po.kl8;

import com.prize.lottery.enums.Kl8Channel;
import com.prize.lottery.value.ForecastValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Kl8IcaiInfoPo {

    private Long          id;
    private String        masterId;
    private String        period;
    private ForecastValue xd1;
    private ForecastValue xd2;
    private ForecastValue xd3;
    private ForecastValue xd4;
    private ForecastValue xd5;
    private ForecastValue xd6;
    private ForecastValue xd7;
    private ForecastValue xd8;
    private ForecastValue xd9;
    private ForecastValue xd10;
    private ForecastValue xd11;
    private ForecastValue xd12;
    private ForecastValue xd13;
    private ForecastValue xd14;
    private ForecastValue xd15;
    private ForecastValue xd20;
    private ForecastValue sk1;
    private ForecastValue sk2;
    private ForecastValue sk3;
    private ForecastValue sk4;
    private ForecastValue sk5;
    private LocalDateTime calcTime;
    private LocalDateTime gmtCreate;

    /**
     * 计算数据命中信息
     *
     * @param balls 开奖号码
     */
    public void calcHit(List<String> balls) {
        for (Kl8Channel channel : Kl8Channel.values()) {
            channel.calcHit(channel.forecastValue(this), balls);
        }
    }

}
