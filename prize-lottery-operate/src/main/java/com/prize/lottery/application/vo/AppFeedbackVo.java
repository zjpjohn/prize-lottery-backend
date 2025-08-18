package com.prize.lottery.application.vo;

import com.prize.lottery.infrast.persist.po.AppFeedbackPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AppFeedbackVo extends AppFeedbackPo {

    private String appName;

}
