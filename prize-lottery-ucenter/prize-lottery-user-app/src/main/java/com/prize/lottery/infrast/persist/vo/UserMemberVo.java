package com.prize.lottery.infrast.persist.vo;

import com.prize.lottery.infrast.persist.po.UserMemberPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserMemberVo extends UserMemberPo {

    private String nickname;
    private String phone;

}
