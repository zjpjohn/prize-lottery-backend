package com.prize.lottery.po.pl3;

import com.prize.lottery.enums.WarningEnums;
import com.prize.lottery.value.WarningValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Pl3EarlyWarningPo {

    private Long         id;
    /**
     * 预警期号
     */
    private String       period;
    /**
     * 预警类型
     */
    private WarningEnums type;
    /**
     * 预警值
     */
    private WarningValue warn;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

}
