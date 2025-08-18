package com.prize.lottery.application.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MasterBattleRankVo<T> {

    //是否已在列表中
    private Integer battled;
    //是否已浏览
    private Integer browsed;
    //专家排名信息
    private T       masterRank;

    public MasterBattleRankVo(Integer browsed, T masterRank) {
        this.browsed    = browsed;
        this.masterRank = masterRank;
    }
}
