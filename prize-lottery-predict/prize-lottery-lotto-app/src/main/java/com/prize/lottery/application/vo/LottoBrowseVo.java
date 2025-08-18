package com.prize.lottery.application.vo;

import com.prize.lottery.enums.BrowseType;
import com.prize.lottery.enums.LotteryEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LottoBrowseVo {

    private String        seqNo;
    private Long          userId;
    private String        phone;
    private String        period;
    private LotteryEnum   type;
    private BrowseType    source;
    private String        sourceId;
    private LocalDateTime gmtCreate;

}
