package com.prize.lottery.po.qlc;

import com.prize.lottery.enums.QlcChannel;
import com.prize.lottery.value.StatHitValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QlcHomeMasterPo {

    private Long          id;
    /**
     * 期号
     */
    private String        period;
    /**
     * 专家标识
     */
    private String        masterId;
    /**
     * 数据指标类型
     */
    private QlcChannel    type;
    /**
     * 专家排名
     */
    private Integer       rank;
    /**
     * 命中率信息
     */
    private StatHitValue  rate;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

}
