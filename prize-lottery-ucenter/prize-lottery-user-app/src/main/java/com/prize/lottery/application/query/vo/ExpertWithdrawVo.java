package com.prize.lottery.application.query.vo;

import com.prize.lottery.infrast.persist.po.ExpertWithdrawPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExpertWithdrawVo extends ExpertWithdrawPo {

    private String nickname;
    private String phone;

}
