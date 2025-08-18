package com.prize.lottery.po.master;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MasterLotteryPo {

    private Long          id;
    private String        type;
    private String        masterId;
    private String        sourceId;
    private Integer       source;
    private Integer       level;
    private String        latest;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
