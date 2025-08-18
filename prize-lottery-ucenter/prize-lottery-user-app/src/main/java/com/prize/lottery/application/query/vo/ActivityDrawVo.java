package com.prize.lottery.application.query.vo;

import com.prize.lottery.infrast.persist.po.ActivityChancePo;
import com.prize.lottery.infrast.persist.po.ActivityDrawPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityDrawVo extends ActivityDrawPo {

    //抽奖用户名
    private String                 nickname;
    //抽奖手机号
    private String                 phone;
    //用户抽奖机会
    private List<ActivityChancePo> chances;

}
