package com.prize.lottery.domain.master.model;

import com.prize.lottery.enums.LotteryEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MasterLottery {

    private String  type;
    private String  masterId;
    private Integer source;
    private String  sourceId;
    private Integer level;
    private Integer browse;
    private Integer subscribe;
    private String  latest;

    /**
     * 创建彩票专家记录信息
     *
     * @param type     彩票类型
     * @param masterId 专家标识
     * @param sourceId 来源标识
     * @param source   来源渠道
     */
    public static MasterLottery create(LotteryEnum type, String masterId, String sourceId, Integer source) {
        MasterLottery lottery = new MasterLottery();
        lottery.setType(type.getType());
        lottery.setMasterId(masterId);
        lottery.setSource(source);
        lottery.setSourceId(sourceId);
        return lottery;
    }

}
