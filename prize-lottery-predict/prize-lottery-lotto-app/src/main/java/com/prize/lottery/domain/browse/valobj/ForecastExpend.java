package com.prize.lottery.domain.browse.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class ForecastExpend implements Serializable {

    private final Integer expend;
    private final Integer bounty;

    public static ForecastExpend of(Integer expend) {
        return new ForecastExpend(expend, 20);
    }

}
