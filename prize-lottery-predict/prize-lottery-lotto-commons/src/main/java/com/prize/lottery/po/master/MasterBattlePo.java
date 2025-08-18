package com.prize.lottery.po.master;

import com.prize.lottery.enums.LotteryEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MasterBattlePo {

    private Long          id;
    private LotteryEnum   type;
    private Long          userId;
    private String        masterId;
    private String        period;
    private Integer       sort;
    private Integer       state;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
