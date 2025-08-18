package com.prize.lottery.infrast.spider.skill.domain;

import lombok.Data;

@Data
public class RequestHeader {

    private String  uuid            = "686b3b8f0d234c709b4da50d7fd82eb3";
    private String  platformCode    = "h5mobile";
    private String  appVersion      = "4.0.2";
    private String  platformVersion = "4.0.2";
    private String  cmdName         = "h5_zz";
    private String  brand           = "szcapp";
    private Integer cmdId           = 0;
    private Integer action;

    public RequestHeader(Integer action) {
        this.action = action;
    }
}
