package com.prize.lottery.po.qlc;

import com.prize.lottery.enums.QlcChannel;
import com.prize.lottery.value.ForecastValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QlcIcaiPo {

    private Long          id;
    private String        period;
    private String        masterId;
    private ForecastValue red1;
    private ForecastValue red2;
    private ForecastValue red3;
    private ForecastValue red12;
    private ForecastValue red18;
    private ForecastValue red22;
    private ForecastValue kill3;
    private ForecastValue kill6;
    private LocalDateTime calcTime;
    private LocalDateTime gmtCreate;

    /**
     * 计算预测数据命中信息
     *
     * @param reds 开奖号码
     */
    public void calcHit(List<String> reds) {
        for (QlcChannel channel : QlcChannel.values()) {
            channel.calcHit(channel.forecastValue(this), reds);
        }
    }
}
