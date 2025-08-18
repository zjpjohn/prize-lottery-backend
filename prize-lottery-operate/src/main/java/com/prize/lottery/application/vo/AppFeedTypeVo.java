package com.prize.lottery.application.vo;

import lombok.Data;

import java.util.List;

@Data
public class AppFeedTypeVo {

    private String       appNo;
    private String       version;
    private List<String> types;

}
