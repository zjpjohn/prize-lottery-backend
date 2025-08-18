package com.prize.lottery.vo.pl3;

import com.prize.lottery.value.StatHitValue;
import com.prize.lottery.vo.BaseMasterRank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Pl3MasterMulRankVo extends BaseMasterRank {

    /**
     * 三胆命中率
     */
    private StatHitValue dan3;
    /**
     * 6码命中率
     */
    private StatHitValue com6;
    /**
     * 7码命中率
     */
    private StatHitValue com7;
    /**
     * 杀一码命中率
     */
    private StatHitValue kill1;
}
