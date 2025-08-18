package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.CommonState;
import com.prize.lottery.infrast.persist.valobj.AppBanner;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppBannerPo {

    private Long          id;
    //应用表示
    private String        appNo;
    //banner所属页面标识
    private String      page;
    //banner集合
    private AppBanner   banner;
    private CommonState state;
    private String      remark;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
