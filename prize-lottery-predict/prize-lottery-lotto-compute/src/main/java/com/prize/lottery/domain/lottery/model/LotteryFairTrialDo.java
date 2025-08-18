package com.prize.lottery.domain.lottery.model;

import com.prize.lottery.enums.LotteryEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class LotteryFairTrialDo {

    private Long        id;
    private LotteryEnum type;
    private String      period;
    private String      ball;

    public LotteryFairTrialDo(LotteryEnum type, String period, String ball) {
        this.type   = type;
        this.period = period;
        this.ball   = ball;
    }

    public static List<LotteryFairTrialDo> create(LotteryEnum type, List<Pair<String, String>> values) {
        return values.stream()
                     .map(p -> new LotteryFairTrialDo(type, p.getKey(), p.getKey()))
                     .collect(Collectors.toList());
    }

}
