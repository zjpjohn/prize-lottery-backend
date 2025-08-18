package com.prize.lottery.infrast.persist.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prize.lottery.infrast.persist.enums.AgentLevel;
import com.prize.lottery.infrast.persist.enums.RegisterChannel;
import com.prize.lottery.infrast.persist.enums.UserState;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAdmInfoVo {

    /**
     * 基础信息
     */
    private Long            userId;
    private String          nickname;
    private String          phone;
    private RegisterChannel channel;
    private UserState       state;
    private Integer         expert;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private LocalDateTime   createTime;
    /**
     * 邀请账户信息
     */
    private AgentLevel      level;
    private String          invCode;
    private String          invUri;
    private Integer         invites;
    private Integer         rewards;
    /**
     * 登录信息
     */
    private Integer         loginState;
    private String          loginIp;
    private LocalDateTime   loginTime;
    private LocalDateTime   expireAt;

}
