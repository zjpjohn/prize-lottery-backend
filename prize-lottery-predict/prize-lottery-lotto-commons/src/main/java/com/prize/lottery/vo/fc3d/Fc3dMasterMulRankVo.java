package com.prize.lottery.vo.fc3d;

import com.prize.lottery.value.StatHitValue;
import com.prize.lottery.vo.BaseMasterRank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 福彩3D专家综合排名信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Fc3dMasterMulRankVo extends BaseMasterRank {

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
