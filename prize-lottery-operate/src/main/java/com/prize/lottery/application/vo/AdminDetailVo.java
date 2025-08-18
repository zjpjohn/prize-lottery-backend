package com.prize.lottery.application.vo;

import com.prize.lottery.infrast.persist.po.AdministratorPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminDetailVo extends AdministratorPo {

    private String        loginIp;
    private LocalDateTime loginTime;
    private LocalDateTime expireAt;

}
