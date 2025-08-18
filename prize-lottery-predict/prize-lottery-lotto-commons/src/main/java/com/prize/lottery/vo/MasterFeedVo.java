package com.prize.lottery.vo;

import com.prize.lottery.enums.FeedsType;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.value.MasterValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MasterFeedVo {

    private Long        id;
    private LotteryEnum type;
    private MasterValue master;
    private String    field;
    private FeedsType feedType;
    private String    rateText;
    private String        hitText;
    private String        period;
    private Integer       renew;
    private String        renewPeriod;
    private Integer       state;
    private LocalDateTime timestamp;

}
