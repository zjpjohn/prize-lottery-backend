package com.prize.lottery.po.dlt;

import com.prize.lottery.enums.DltChannel;
import com.prize.lottery.value.ForecastValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DltIcaiPo {

    private Long          id;
    private String        masterId;
    private String        period;
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
    private LocalDateTime calcTime;
    private LocalDateTime gmtCreate;

    /**
     * 计算数据命中信息
     *
     * @param reds  红球奖号
     * @param blues 蓝球奖号
     */
    public void calcHit(List<String> reds, List<String> blues) {
        for (DltChannel channel : DltChannel.values()) {
            channel.calcHit(channel.forecastValue(this), reds, blues);
        }
    }
}
