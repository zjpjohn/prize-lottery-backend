package com.prize.lottery.infrast.persist.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppInfoPo {

    private Long          id;
    private String        seqNo;
    private String        name;
    private String        logo;
    private String        shareUri;
    private String        openInstall;
    private String        copyright;
    private String        corp;
    private String        telephone;
    private String        address;
    private String        record;
    private String        remark;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
