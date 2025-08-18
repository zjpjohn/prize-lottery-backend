package com.prize.lottery.infrast.commons;

import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Range;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;


@Data
public class SumRange {

    @PositiveOrZero(message = "最小值为正数")
    private Integer min;
    @PositiveOrZero(message = "最大值为正数")
    private Integer max;

    public Range<Integer> range() {
        if (max != null && min != null) {
            Assert.state(max >= min, ResponseHandler.SUM_RANGE_ERROR);
            return Range.closed(min, max);
        }
        if (max != null) {
            return Range.atMost(max);
        }
        if (min != null) {
            return Range.atLeast(min);
        }
        return null;
    }
}
