package com.prize.lottery.po.ssq;

import com.prize.lottery.enums.SsqChannel;
import com.prize.lottery.value.ForecastValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SsqIcaiPo {

    private Long   id;
    private String period;
    private String masterId;

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
    private LocalDateTime calcTime;
    private LocalDateTime gmtCreate;

    /**
     * 计算预测数据命中率
     *
     * @param reds 开奖红球号码
     * @param blue 开讲蓝球号码
     */
    public void calcHit(List<String> reds, String blue) {
        for (SsqChannel channel : SsqChannel.values()) {
            channel.calcHit(channel.forecastValue(this), reds, blue);
        }
    }
}
