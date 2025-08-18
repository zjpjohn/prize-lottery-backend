package com.prize.lottery.po.master;

import com.prize.lottery.enums.BrowseType;
import com.prize.lottery.enums.LotteryEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MasterBrowsePo {

    private Long          id;
    private String        seqNo;
    private Long          userId;
    private String      period;
    private LotteryEnum type;
    private BrowseType  source;
    private String      sourceId;
    private LocalDateTime gmtCreate;

}
