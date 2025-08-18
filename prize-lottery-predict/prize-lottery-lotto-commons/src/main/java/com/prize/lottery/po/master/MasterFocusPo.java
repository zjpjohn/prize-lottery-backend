package com.prize.lottery.po.master;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MasterFocusPo {

    private Long          id;
    private Long          userId;
    private String        masterId;
    private LocalDateTime gmtCreate;

}
