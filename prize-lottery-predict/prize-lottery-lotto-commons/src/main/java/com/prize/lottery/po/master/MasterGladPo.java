package com.prize.lottery.po.master;

import com.prize.lottery.enums.LotteryEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MasterGladPo {

    private Long          id;
    private String        masterId;
    private LotteryEnum   lottery;
    private String        period;
    private String        content;
    private Integer       type;
    private LocalDateTime gmtCreate;

}
