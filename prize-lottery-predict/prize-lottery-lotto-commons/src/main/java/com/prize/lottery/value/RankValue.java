package com.prize.lottery.value;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RankValue implements Comparable<RankValue> {

    //当前排名
    private Integer rank;
    //当前排名权重
    private Double  weight;

    public RankValue(Double weight) {
        this(0, weight);
    }

    public RankValue(Integer rank, Double weight) {
        this.rank   = rank;
        this.weight = weight;
    }

    @Override
    public int compareTo(RankValue other) {
        double result = this.weight - other.getWeight();
        if (result > 0) {
            return 1;
        }
        if (result < 0) {
            return -1;
        }
        return 0;
    }

}
