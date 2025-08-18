package com.prize.lottery.enums;

import com.prize.lottery.value.StatHitValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Getter
@AllArgsConstructor
public enum RateLevel {
    NEAR(7),
    MIDDLE(15),
    HIGH(30);

    private final int level;

    public StatHitValue calcRate(List<Integer> hits, int throttle, boolean full, int fullThrottle) {
        StatHitValue  rateValue     = new StatHitValue();
        List<Integer> nearHits      = hits.stream().limit(this.level).toList();
        long          nearCondition = nearHits.stream().filter(item -> item >= throttle).count();
        double nearRate = BigDecimal.valueOf(nearCondition / (this.level * 1.0))
                                    .setScale(2, RoundingMode.HALF_UP)
                                    .doubleValue();
        rateValue.setRate(nearRate);
        rateValue.setCount(this.level + "中" + nearCondition);
        //计算连续命中
        int nearHit = calcSeries(nearHits, throttle);
        rateValue.setSeries(nearHit);
        if (full) {
            //计算全部命中
            long nearFullHit = nearHits.stream().filter(item -> item >= fullThrottle).count();
            double nearFullRate = BigDecimal.valueOf(nearFullHit / (this.level * 1.0))
                                            .setScale(2, RoundingMode.HALF_UP)
                                            .doubleValue();
            rateValue.setFullRate(nearFullRate);
        }
        return rateValue;
    }

    private Integer calcSeries(List<Integer> hits, int throttle) {
        int series = 0;
        for (Integer hit : hits) {
            if (hit < throttle) {
                break;
            }
            series = series + 1;
        }
        return series;
    }
}
