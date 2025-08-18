package com.prize.lottery.infrast.persist.po;

import com.prize.lottery.infrast.persist.enums.CommonState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppVerifyPo {

    private Long          id;
    private String        appNo;
    private String        appPack;
    private String        signature;
    private String      authKey;
    //配置状态
    private CommonState state;
    //授权成功响应码
    private String      success;
    //取消授权响应码
    private String        cancel;
    //降级授权响应码集合，使用","分隔
    private String        downgrades;
    private String        remark;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;

}
