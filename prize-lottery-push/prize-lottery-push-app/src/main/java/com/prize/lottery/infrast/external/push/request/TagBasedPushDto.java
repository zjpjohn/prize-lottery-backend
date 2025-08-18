package com.prize.lottery.infrast.external.push.request;

import com.prize.lottery.infrast.persist.enums.OpenType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TagBasedPushDto {

    private Long          appKey;
    private String        pushType;
    private String        tags;
    private Boolean       online;
    private LocalDateTime pushTime;
    private String        title;
    private String        body;
    private String        notifyType;
    private OpenType      openType;
    private String        activity;
    private String        url;
    private Integer       barType;
    private Integer       barPriority;
    private String        notifyChannel;
    private String        extParams;

}
