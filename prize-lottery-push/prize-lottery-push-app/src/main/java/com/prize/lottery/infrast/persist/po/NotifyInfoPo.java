package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.CommonState;
import com.prize.lottery.infrast.persist.enums.NotifyNotice;
import com.prize.lottery.infrast.persist.enums.OpenType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotifyInfoPo {

    private Long          id;
    private Long          appKey;
    private Long          groupId;
    private Integer       type;
    private Integer       hour;
    private String        title;
    private String        body;
    private NotifyNotice  notice;
    private Integer       barType;
    private Integer       barPriority;
    private String        extParams;
    private OpenType      openType;
    private String        openUrl;
    private String        openActivity;
    private String        channel;
    private CommonState   state;
    private Integer       online;
    private String        remark;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
