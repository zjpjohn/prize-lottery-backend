package com.prize.lottery.value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BallIndex implements Comparable<BallIndex> {

    private String ball;
    private Double index;

    @Override
    public int compareTo(BallIndex index) {
        int compared = this.index.compareTo(index.getIndex());
        if (compared == 0) {
            return this.ball.compareTo(index.getBall());
        }
        return compared;
    }
}
