package com.prize.lottery.application.query.vo;

import com.prize.lottery.infrast.persist.po.UserWithdrawPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserWithdrawVo extends UserWithdrawPo {

    private String nickname;
    private String phone;

}
