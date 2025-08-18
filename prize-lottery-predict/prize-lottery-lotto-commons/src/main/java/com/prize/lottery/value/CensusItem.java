package com.prize.lottery.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CensusItem implements Comparable<CensusItem> {

    private final String  ball;
    private final Long value;

    @Override
    public int compareTo(CensusItem item) {
        int compare = this.value.compareTo(item.value);
        if (compare != 0) {
            return compare;
        }
        return this.ball.compareTo(item.ball);
    }

}
