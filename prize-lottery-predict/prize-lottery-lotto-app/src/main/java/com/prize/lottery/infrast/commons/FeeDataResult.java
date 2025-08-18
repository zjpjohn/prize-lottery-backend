package com.prize.lottery.infrast.commons;

import com.prize.lottery.domain.browse.valobj.ForecastExpend;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FeeDataResult<T> {

    private final String         period;
    private final boolean        feeRequired;
    private final T              data;
    private final ForecastExpend expend;

    public static <V> FeeDataResult<V> success(V data, String period) {
        return new FeeDataResult<>(period, false, data, null);
    }

    public static <V> FeeDataResult<V> failure(String period) {
        return new FeeDataResult<>(period, true, null, null);
    }

    public static <V> FeeDataResult<V> failure(String period, ForecastExpend expend) {
        return new FeeDataResult<>(period, true, null, expend);
    }

    public static <V> FeeDataResult<V> failure(String period, V data, ForecastExpend expend) {
        return new FeeDataResult<>(period, true, data, expend);
    }

}
