package com.prize.lottery.infrast.persist.po;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FeedbackTypePo {

    //默认适用全部版本
    public static final String DEFAULT_VERSION = "*";

    private Long      id;
    private String    appNo;
    private String    type;
    private String    suitVer;
    private String    remark;
    private Integer   sort;
    private LocalDate gmtCreate;
    private LocalDate gmtModify;

    public boolean suitableVersion(String version) {
        return suitVer.equals(DEFAULT_VERSION) || suitVer.compareTo(version) <= 0;
    }

}
