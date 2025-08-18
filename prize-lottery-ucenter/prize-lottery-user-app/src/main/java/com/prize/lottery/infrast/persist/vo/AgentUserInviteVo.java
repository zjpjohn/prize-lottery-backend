package com.prize.lottery.infrast.persist.vo;

import com.prize.lottery.infrast.persist.po.UserInvitePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AgentUserInviteVo extends UserInvitePo {

    //账户名
    private String nickname;
    //手机号
    private String phone;

}
