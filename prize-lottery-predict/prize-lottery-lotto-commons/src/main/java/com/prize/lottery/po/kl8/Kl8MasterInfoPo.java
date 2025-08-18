package com.prize.lottery.po.kl8;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Kl8MasterInfoPo {

    private Long          id;
    private String        masterId;
    private String        name;
    private String        logo;
    private Long          thirdId;
    private String        thirdName;
    private String        latest;
    private Integer       browse;
    private LocalDateTime gmtCreate;

}
