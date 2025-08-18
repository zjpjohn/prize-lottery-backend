package com.prize.lottery.vo;

import com.prize.lottery.enums.BrowseType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.MasterValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BrowseRecordVo {

    private Long          id;
    private Long          userId;
    private String      period;
    private LotteryEnum type;
    private String      sourceId;
    private BrowseType    source;
    private MasterValue   master;
    private LocalDateTime gmtCreate;
}
