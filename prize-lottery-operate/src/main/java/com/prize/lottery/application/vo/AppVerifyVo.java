package com.prize.lottery.application.vo;

import lombok.Data;

@Data
public class AppVerifyVo {

    private String appNo;
    private String authKey;
    private String success;
    private String cancel;
    private String downgrades;

}
